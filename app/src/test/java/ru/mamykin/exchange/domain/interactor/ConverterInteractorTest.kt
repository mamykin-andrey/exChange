package ru.mamykin.exchange.domain.interactor

import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.mamykin.exchange.TestSchedulerRule
import ru.mamykin.exchange.core.extension.skip
import ru.mamykin.exchange.data.repository.RatesRepository
import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.domain.entity.RateList
import java.util.*
import java.util.concurrent.TimeUnit

class ConverterInteractorTest {

    companion object {
        const val RUB_CURRENCY = "RUB"
    }

    @get:Rule
    val testSchedulerRule = TestSchedulerRule()

    @Mock
    lateinit var ratesRepository: RatesRepository

    val today = Date()
    val rateList1 = RateList(RUB_CURRENCY, today, listOf(Rate("USD", 0.015f)))
    val rateList2 = RateList(RUB_CURRENCY, today, listOf(Rate("EUR", 0.013f)))
    val rateList3 = RateList(RUB_CURRENCY, today, listOf(Rate("USD", 0.015f), Rate("EUR", 0.013f)))

    lateinit var interactor: ConverterInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interactor = ConverterInteractor(ratesRepository)
        whenever(ratesRepository.getRates(RUB_CURRENCY))
                .thenReturn(Single.just(rateList1), Single.just(rateList2))
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun getRates_return2Items_after2Seconds() {
        val testObserver = interactor.getRates(RUB_CURRENCY, 1f).test()
        testSchedulerRule.testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)

        testObserver.assertNoErrors().assertValueCount(2)
    }

    @Test
    fun getRates_doesNotStopUpdates_whenErrorOccurs() {
        whenever(ratesRepository.getRates(RUB_CURRENCY)).thenReturn(
                Single.just(rateList1), Single.error(RuntimeException()), Single.just(rateList2))

        val testObserver = interactor.getRates(RUB_CURRENCY, 1f).test()
        testSchedulerRule.testScheduler.advanceTimeBy(3, TimeUnit.SECONDS)

        testObserver.assertNoErrors().assertValueCount(2)
    }

    @Test
    fun getRates_addCurrentCurrencyRateToTopOfList() {
        val testObserver = interactor.getRates(RUB_CURRENCY, 1f).test()
        testSchedulerRule.testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        testObserver.assertNoErrors().assertValue { it.rates.first().code == RUB_CURRENCY }
    }

    @Test
    fun getRates_returnCurrenciesAmountWithExchangeRate() {
        val expectedRates = listOf(Rate("USD", 1.5f), Rate("EUR", 1.3000001f))
        whenever(ratesRepository.getRates(RUB_CURRENCY)).thenReturn(Single.just(rateList3))

        val testObserver = interactor.getRates(RUB_CURRENCY, 100f).test()
        testSchedulerRule.testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        testObserver.assertNoErrors().assertValue { it.rates.skip(1) == expectedRates }
    }
}