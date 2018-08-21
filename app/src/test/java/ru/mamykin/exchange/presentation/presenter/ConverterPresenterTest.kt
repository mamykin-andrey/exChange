package ru.mamykin.exchange.presentation.presenter

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.mamykin.exchange.TestSchedulersProvider
import ru.mamykin.exchange.domain.entity.RateList
import ru.mamykin.exchange.domain.interactor.ConverterInteractor
import ru.mamykin.exchange.presentation.view.ConverterView
import ru.mamykin.exchange.presentation.view.`ConverterView$$State`
import java.util.*

class ConverterPresenterTest {

    companion object {
        const val RUB_CURRENCY = "RUB"
    }

    @Mock
    lateinit var interactor: ConverterInteractor
    @Mock
    lateinit var viewState: `ConverterView$$State`
    @Mock
    lateinit var view: ConverterView

    val schedulersProvider = TestSchedulersProvider()

    lateinit var presenter: ConverterPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ConverterPresenter(interactor, schedulersProvider)
        presenter.setViewState(viewState)
    }

    @Test
    fun onFirstViewAttach_shouldShowRateList_whenInteractorReturnsRateList() {
        val rateList = RateList(RUB_CURRENCY, Date(), listOf())
        whenever(interactor.getRates(RUB_CURRENCY, 1f)).thenReturn(Observable.just(rateList))

        presenter.attachView(view)

        verify(interactor).getRates(RUB_CURRENCY, 1f)
        verify(viewState).showRateList(rateList)
    }

    @Test
    fun onFirstViewAttach_shouldShowLoadingError_whenInteractorReturnsError() {
        whenever(interactor.getRates(RUB_CURRENCY, 1f)).thenReturn(Observable.error(RuntimeException()))

        presenter.attachView(view)

        verify(interactor).getRates(RUB_CURRENCY, 1f)
        verify(viewState).showLoadingError()
    }

    @Test
    fun onCurrencyOrAmountChanged_shouldLoadRateList() {
        val newCurrencyCode = "EUR"
        val newCurrencyAmount = 1.0f
        whenever(interactor.getRates(newCurrencyCode, newCurrencyAmount))
                .thenReturn(Observable.just(RateList(newCurrencyCode, Date(), listOf())))

        presenter.onCurrencyOrAmountChanged(newCurrencyCode, newCurrencyAmount)

        verify(interactor).getRates(newCurrencyCode, newCurrencyAmount)
    }

    @Test
    fun onViewStop_shouldStopRatesUpdates() {
        whenever(interactor.getRates(any(), any()))
                .thenReturn(Observable.just(RateList("RUB", Date(), listOf())))

        presenter.attachView(view)
        verify(interactor).getRates(any(), any())

        presenter.onViewStop()
        verifyNoMoreInteractions(interactor)
    }

    @Test
    fun onViewStart_shouldStartRatesUpdates_whenViewIsFromStopState() {
        whenever(interactor.getRates(any(), any()))
                .thenReturn(Observable.just(RateList("RUB", Date(), listOf())))

        presenter.attachView(view)
        verify(interactor).getRates(any(), any())

        presenter.onViewStop()
        presenter.onViewStart()
        verify(interactor, times(2)).getRates(any(), any())
    }
}