package ru.mamykin.exchange.presentation.presenter

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
) : BasePresenter<ConverterView>() {

    private var getRatesDisposable: Disposable? = null
    private var currentCurrencyRate: Rate = Rate("RUB", 1.0f)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadRates("RUB")
    }

    fun onCurrencySelected(currency: String) = loadRates(currency)

    private fun loadRates(currency: String) {
        getRatesDisposable?.dispose()
        getRatesDisposable = interactor.getRates(currency)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.mainThread())
                .subscribe({ viewState.showRateList(it) }, { viewState.showLoadingError() })
    }

    override fun onDestroy() {
        super.onDestroy()
        getRatesDisposable?.dispose()
    }
}