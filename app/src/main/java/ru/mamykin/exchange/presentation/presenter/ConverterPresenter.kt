package ru.mamykin.exchange.presentation.presenter

import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.Disposable
import ru.mamykin.exchange.core.mvp.BasePresenter
import ru.mamykin.exchange.core.scheduler.SchedulersProvider
import ru.mamykin.exchange.domain.interactor.ConverterInteractor
import ru.mamykin.exchange.presentation.view.ConverterView
import javax.inject.Inject

@InjectViewState
class ConverterPresenter @Inject constructor(
        private val interactor: ConverterInteractor,
        private val schedulersProvider: SchedulersProvider
) : BasePresenter<ConverterView>(), LifecycleObserver {

    private var ratesDisposable: Disposable? = null
    private var currency = "RUB"
    private var amount = 1.0f

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLoading(true)
        loadRates(currency, amount, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        ratesDisposable?.dispose()
    }

    @OnLifecycleEvent(ON_RESUME)
    fun onViewStart() {
        if (ratesDisposable?.isDisposed == true) {
            loadRates(currency, amount, true)
        }
    }

    @OnLifecycleEvent(ON_STOP)
    fun onViewStop() {
        ratesDisposable?.dispose()
    }

    fun onCurrencyOrAmountChanged(newCurrency: String, newAmount: Float) {
        if (interactor.isCurrencyEquals(newCurrency, currency, newAmount, amount))
            return

        val currencyChanged = newCurrency != currency
        if (currencyChanged) viewState.showLoading(true)
        loadRates(newCurrency, newAmount, currencyChanged).also {
            this.currency = newCurrency
            this.amount = newAmount
        }
    }

    private fun loadRates(currency: String, amount: Float, force: Boolean) {
        ratesDisposable?.dispose()
        ratesDisposable = interactor.getRates(currency, amount, force)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.mainThread())
                .doOnNext { viewState.showLoading(false) }
                .subscribe({ viewState.showRateList(it) }, { viewState.showLoadingError() })
    }
}