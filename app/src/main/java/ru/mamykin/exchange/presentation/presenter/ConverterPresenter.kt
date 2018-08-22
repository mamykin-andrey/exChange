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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLoading(true)
        loadRates("RUB", 1.0f)
    }

    override fun onDestroy() {
        super.onDestroy()
        ratesDisposable?.dispose()
    }

    @OnLifecycleEvent(ON_RESUME)
    fun onViewStart() {
        ratesDisposable?.isDisposed?.let { loadRates() }
    }

    @OnLifecycleEvent(ON_STOP)
    fun onViewStop() {
        ratesDisposable?.dispose()
    }

    fun onCurrencyOrAmountChanged(newCurrency: String, newAmount: Float) {
        viewState.showLoading(true)
        loadRates(newCurrency, newAmount)
    }

    private fun loadRates(currency: String? = null, amount: Float? = null) {
        ratesDisposable?.dispose()
        ratesDisposable = interactor.getRates(currency, amount)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.mainThread())
                .doOnNext { viewState.showLoading(false) }
                .subscribe({ viewState.showRateList(it) }, { viewState.showLoadingError() })
    }
}