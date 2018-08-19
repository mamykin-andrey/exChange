package ru.mamykin.exchange.data.repository

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.domain.entity.RateList
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

        val testObserver = repository.getRates(TEST_CURRENCY).test()

        testObserver.assertComplete().assertValue(rateList)
        verify(dataSource).getRates(TEST_CURRENCY)
    }

    @Test
    fun getRates_returnError_whenDataSourceReturnError() {
        whenever(dataSource.getRates(TEST_CURRENCY)).thenReturn(Single.error(RuntimeException()))

        val testObserver = repository.getRates(TEST_CURRENCY).test()

        testObserver.assertNotComplete().assertError(RuntimeException::class.java)
        verify(dataSource).getRates(TEST_CURRENCY)
    }
}