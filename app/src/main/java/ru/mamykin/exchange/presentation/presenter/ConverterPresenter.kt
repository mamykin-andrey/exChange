package ru.mamykin.exchange.presentation.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.Disposable
import ru.mamykin.exchange.core.mvp.BasePresenter
import ru.mamykin.exchange.core.scheduler.SchedulersProvider
import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.domain.interactor.ConverterInteractor
import ru.mamykin.exchange.presentation.view.ConverterView
import javax.inject.Inject

@InjectViewState
class ConverterPresenter @Inject constructor(
        private val interactor: ConverterInteractor,
        private val schedulersProvider: SchedulersProvider
) : BasePresenter<ConverterView>(), LifecycleObserver {

    private var getRatesDisposable: Disposable? = null
    private var currentCurrencyRate = Rate("RUB", 1.0f)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLoading(true)
        loadRates(currentCurrencyRate.code, currentCurrencyRate.amount)
    }

    override fun onDestroy() {
        super.onDestroy()
        getRatesDisposable?.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onViewStart() {
        loadRates(currentCurrencyRate.code, currentCurrencyRate.amount)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onViewStop() {
        getRatesDisposable?.dispose()
    }

    fun onCurrencyOrAmountChanged(currencyCode: String, amount: Float) {
        val newCurrencyRate = Rate(currencyCode, amount)
        if (currentCurrencyRate != newCurrencyRate) {
            currentCurrencyRate = newCurrencyRate
            loadRates(currencyCode, amount)
            viewState.showLoading(true)
        }
    }

    private fun loadRates(currency: String, amount: Float) {
        getRatesDisposable?.dispose()
        getRatesDisposable = interactor.getRates(currency, amount)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.mainThread())
                .doOnNext { viewState.showLoading(false) }
                .subscribe({ viewState.showRateList(it) }, { viewState.showLoadingError() })
    }
}