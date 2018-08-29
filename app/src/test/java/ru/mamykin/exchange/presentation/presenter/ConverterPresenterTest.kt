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

    lateinit var presenter: ConverterPresenter
    val rateList = RateList(RUB_CURRENCY, Date(), listOf())

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ConverterPresenter(interactor, TestSchedulersProvider())
        presenter.setViewState(viewState)
    }

    @Test
    fun onFirstViewAttach_shouldShowLoading() {
        whenever(interactor.getRates(RUB_CURRENCY, 1f, true)).thenReturn(Observable.just(rateList))

        presenter.attachView(view)

        verify(viewState).showLoading(true)
    }

    @Test
    fun onFirstViewAttach_shouldShowRateList_whenInteractorReturnsRateList() {
        whenever(interactor.getRates(RUB_CURRENCY, 1f, true)).thenReturn(Observable.just(rateList))

        presenter.attachView(view)

        verify(interactor).getRates(RUB_CURRENCY, 1f, true)
        verify(viewState).showRateList(rateList)
    }

    @Test
    fun onViewStop_shouldStopRatesUpdates() {
        whenever(interactor.getRates(any(), any(), any()))
                .thenReturn(Observable.just(RateList("RUB", Date(), listOf())))

        presenter.attachView(view)
        verify(interactor).getRates(any(), any(), any())

        presenter.onViewStop()
        verifyNoMoreInteractions(interactor)
    }

    @Test
    fun onViewStart_shouldStartRatesUpdates_whenViewIsFromStopState() {
        whenever(interactor.getRates(any(), any(), any()))
                .thenReturn(Observable.just(RateList("RUB", Date(), listOf())))

        presenter.attachView(view)
        verify(interactor).getRates(any(), any(), any())

        presenter.onViewStop()
        presenter.onViewStart()
        verify(interactor, times(2)).getRates(any(), any(), any())
    }

    @Test
    fun onCurrencyOrAmountChanged_shouldLoadRateList_whenNeedRecalculateReturnsTrue() {
        val newCurrency = "EUR"
        val newAmount = 1.0f
        whenever(interactor.getRates(newCurrency, newAmount, true)).thenReturn(Observable.just(rateList))
        whenever(interactor.needRecalculate(any(), any(), any(), any())).thenReturn(true)

        presenter.onCurrencyOrAmountChanged(newCurrency, newAmount)

        verify(interactor).getRates(newCurrency, newAmount, true)
    }

    @Test
    fun onCurrencyOrAmountChanged_shouldNotLoadRateList_whenNeedRecalculateReturnsFalse() {
        whenever(interactor.needRecalculate(any(), any(), any(), any())).thenReturn(false)

        presenter.onCurrencyOrAmountChanged("EUR", 1.0f)

        verify(interactor, times(0)).getRates(any(), any(), any())
    }
}