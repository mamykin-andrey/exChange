package ru.mamykin.exchange.presentation.converter

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.Disposable
import ru.mamykin.exchange.core.Result
import ru.mamykin.exchange.core.mvp.BaseViewModel
import ru.mamykin.exchange.core.rx.SchedulersProvider
import ru.mamykin.exchange.domain.ErrorMapper
import ru.mamykin.exchange.domain.converter.ConverterInteractor
import ru.mamykin.exchange.domain.entity.RateEntity
import ru.mamykin.exchange.presentation.converter.mapper.RateViewDataMapper
import ru.mamykin.exchange.presentation.converter.viewdata.RateViewData
import javax.inject.Inject

class ConverterViewModel @Inject constructor(
    private val interactor: ConverterInteractor,
    override val schedulersProvider: SchedulersProvider,
    private val mapError: ErrorMapper,
    private val mapViewData: RateViewDataMapper
) : BaseViewModel(schedulersProvider) {

    private var ratesDisposable: Disposable? = null
    private var currency = "RUB"
    private var amount = 1.0f

    val isLoading = MutableLiveData(true)
    val rates = MutableLiveData<List<RateViewData>>()
    val error = MutableLiveData<String>()

    fun startRatesLoading() {
        loadRates(currency, amount, true)
    }

    fun stopRatesLoading() {
        ratesDisposable?.dispose()
    }

    fun onCurrencyOrAmountChanged(newCurrency: String, newAmount: Float) {
        // TODO:
//        if (!interactor.needRecalculate(newCurrency, currency, newAmount, amount))
//            return

        val currencyChanged = newCurrency != currency
        loadRates(newCurrency, newAmount, currencyChanged).also {
            this.currency = newCurrency
            this.amount = newAmount
        }
    }

    private fun loadRates(currency: String, amount: Float, force: Boolean) {
        ratesDisposable?.dispose()
        ratesDisposable = interactor.getRates(currency, amount, force)
            .ioToMain()
            .doOnEach { isLoading.value = false }
            .subscribe(::onRatesLoaded)
            .unsubscribeOnDestroy()
    }

    private fun onRatesLoaded(result: Result<List<RateEntity>>) {
        if (result is Result.Success) {
            rates.value = mapViewData.transform(result.value)
        } else if (result is Result.Error) {
            error.value = mapError(result.throwable)
            ratesDisposable?.dispose()
        }
    }
}