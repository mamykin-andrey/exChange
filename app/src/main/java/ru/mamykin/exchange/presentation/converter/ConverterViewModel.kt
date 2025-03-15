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
    private var currentCurrencyCode: String? = null
    private var currentAmount: Float? = null

    val isLoading = MutableLiveData(true)
    val rates = MutableLiveData<List<RateViewData>>()
    val error = MutableLiveData<String>()
    val currentRateChanged = MutableLiveData<Unit>()

    fun startRatesLoading() {
        loadRates(null, null, true)
    }

    fun stopRatesLoading() {
        ratesDisposable?.dispose()
    }

    fun onCurrencyOrAmountChanged(currencyCode: String, amount: Float) {
        if (currencyCode == currentCurrencyCode && amount == currentAmount) return

        val currencyChanged = currencyCode != currentCurrencyCode
        this.currentCurrencyCode = currencyCode
        this.currentAmount = amount
        loadRates(currentCurrencyCode, currentAmount, currencyChanged)
    }

    private fun loadRates(
        currentCurrency: String?,
        currentCurrencyAmount: Float?,
        currencyChanged: Boolean,
    ) {
        ratesDisposable?.dispose()
        ratesDisposable = interactor.getRates(currentCurrency, currentCurrencyAmount, currencyChanged)
            .ioToMain()
            .doOnEach { isLoading.value = false }
            .subscribe { onRatesLoaded(it, currencyChanged) }
            .unsubscribeOnDestroy()
    }

    private fun onRatesLoaded(result: Result<List<RateEntity>>, currencyChanged: Boolean) {
        if (result is Result.Success) {
            rates.value = mapViewData.transform(result.value, currentCurrencyCode)
            if (currencyChanged) {
                currentRateChanged.value = Unit
            }
        } else if (result is Result.Error) {
            error.value = mapError(result.throwable)
            ratesDisposable?.dispose()
        }
    }
}