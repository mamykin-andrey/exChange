package ru.mamykin.exchange.domain.interactor

import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.mamykin.exchange.TestSchedulerRule
import ru.mamykin.exchange.data.repository.RatesRepository
import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.domain.entity.RateList
import java.util.*
import java.util.concurrent.TimeUnit

class ConverterInteractorTest {

    companion object {
        const val TEST_CURRENCY = "RUB"
    }

    @get:Rule
    val testSchedulerRule = TestSchedulerRule()

    @Mock
    lateinit var ratesRepository: RatesRepository

    private val today = Date()
    private val rateList1 = RateList(TEST_CURRENCY, today, listOf(Rate("USD", 0.148f)))
    private val rateList2 = RateList(TEST_CURRENCY, today, listOf(Rate("USD", 0.132f)))

    lateinit var interactor: ConverterInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interactor = ConverterInteractor(ratesRepository)
    }

    @Test
    fun getRates_return2Items_after2Seconds() {
        whenever(ratesRepository.getRates(TEST_CURRENCY))
                .thenReturn(Single.just(rateList1), Single.just(rateList2))

        val testSubscriber = interactor.getRates(TEST_CURRENCY).test()
        testSchedulerRule.testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)

        testSubscriber.assertNoErrors().assertValues(rateList1, rateList2)
    }

    @Test
    fun getRates_doesNotStopUpdates_whenErrorOccurs() {
        whenever(ratesRepository.getRates(TEST_CURRENCY))
                .thenReturn(Single.just(rateList1), Single.error(RuntimeException()), Single.just(rateList2))

        val testSubscriber = interactor.getRates(TEST_CURRENCY).test()
        testSchedulerRule.testScheduler.advanceTimeBy(3, TimeUnit.SECONDS)

        testSubscriber.assertNoErrors().assertValues(rateList1, rateList2)
    }
}