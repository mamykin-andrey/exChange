package ru.mamykin.exchange.data.repository.rates

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.mamykin.exchange.data.model.RateList
import rx.Single
import java.util.*

class RatesRepositoryTest {

    companion object {
        const val TEST_CURRENCY = "RUB"
    }

    @Mock
    lateinit var dataSource: RatesDataSource

    private lateinit var repository: RatesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = RatesRepository(dataSource)
    }

    @Test
    fun getRates_returnRateList_whenDataSourceReturnRateList() {
        val rateList = RateList(TEST_CURRENCY, Date(), listOf())
        whenever(dataSource.getRates(TEST_CURRENCY)).thenReturn(Single.just(rateList))

        val testSubscriber = repository.getRates(TEST_CURRENCY).test()

        testSubscriber.assertCompleted().assertValue(rateList)
        verify(dataSource).getRates(TEST_CURRENCY)
    }

    @Test
    fun getRates_returnError_whenDataSourceReturnError() {
        whenever(dataSource.getRates(TEST_CURRENCY)).thenReturn(Single.error(RuntimeException()))

        val testSubscriber = repository.getRates(TEST_CURRENCY).test()

        testSubscriber.assertNotCompleted().assertError(RuntimeException::class.java)
        verify(dataSource).getRates(TEST_CURRENCY)
    }
}