package ru.mamykin.exchange.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import ru.mamykin.exchange.core.mvp.BasePresenter
import ru.mamykin.exchange.domain.interactor.ConverterInteractor
import ru.mamykin.exchange.presentation.scheduler.SchedulersProvider
import ru.mamykin.exchange.presentation.view.ConverterView
import javax.inject.Inject

@InjectViewState
class ConverterPresenter @Inject constructor(
        private val interactor: ConverterInteractor,
        private val schedulersProvider: SchedulersProvider
) : BasePresenter<ConverterView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        interactor.getRates("RUB")
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.mainThread())
                .subscribe({ viewState.showRateList(it) }, { viewState.showLoadingError() })
                .unsubscribeOnDestroy()
    }
}