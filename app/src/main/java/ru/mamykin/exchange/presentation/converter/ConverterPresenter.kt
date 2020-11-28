package ru.mamykin.exchange.presentation.converter

import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import ru.mamykin.exchange.core.mvp.BasePresenter
import ru.mamykin.exchange.core.rx.SchedulersProvider
import ru.mamykin.exchange.domain.converter.ConverterInteractor
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

@InjectViewState
class ConverterPresenter @Inject constructor(
    private val interactor: ConverterInteractor,
    override val schedulersProvider: SchedulersProvider
) : BasePresenter<ConverterView>(schedulersProvider) {

    private var ratesDisposable: Disposable? = null
    private var currency = "RUB"
    private var amount = 1.0f
    private var isFirstLoading = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLoading(true)
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

    private fun onRatesLoaded(rateList: RateList) {
        if (isFirstLoading) {
            viewState.showLoading(false)
            isFirstLoading = false
        }
        viewState.showRateList(rateList)
    }
}