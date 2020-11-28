package ru.mamykin.exchange.presentation.converter

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.Disposable
import ru.mamykin.exchange.core.mvp.BaseViewModel
import ru.mamykin.exchange.core.rx.SchedulersProvider
import ru.mamykin.exchange.domain.converter.ConverterInteractor
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class ConverterViewModel @Inject constructor(
    private val interactor: ConverterInteractor,
    override val schedulersProvider: SchedulersProvider
) : BaseViewModel(schedulersProvider) {

    private var ratesDisposable: Disposable? = null
    private var currency = "RUB"
    private var amount = 1.0f
    private var isFirstLoading = true

    val isLoading = MutableLiveData(true)
    val rates = MutableLiveData<RateList>()

    init {
        isLoading.value = true
        loadRates(currency, amount, true)
    }

    fun onCurrencyOrAmountChanged(newCurrency: String, newAmount: Float) {
        if (!interactor.needRecalculate(newCurrency, currency, newAmount, amount))
            return

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
            .subscribe(::onRatesLoaded)
            .unsubscribeOnDestroy()
    }

    private fun onRatesLoaded(rates: RateList) {
        if (isFirstLoading) {
            isFirstLoading = false
            isLoading.value = false
        }
        this.rates.value = rates
    }
}