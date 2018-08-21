package ru.mamykin.exchange.data.repository.datasource.remote

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.mamykin.exchange.data.network.model.RateListResponse
import ru.mamykin.exchange.data.network.model.mapper.RateListResponseToRateListMapper
import ru.mamykin.exchange.data.network.RatesApi
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.domain.entity.RateList
import java.util.*

class RatesRemoteDataSourceTest {

    companion object {
        const val TEST_CURRENCY = "RUB"
    }

    @Mock
    lateinit var ratesApi: RatesApi
    @Mock
    lateinit var mapper: RateListResponseToRateListMapper

    lateinit var rateListResponse: RateListResponse
    lateinit var rateList: RateList

    lateinit var dataSource: RatesDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = RatesRemoteDataSource(ratesApi, mapper)
        val today = Date()
        rateListResponse = RateListResponse(
                TEST_CURRENCY,
                today,
                mapOf("EUR" to 0.0130f, "USD" to 0.0148f)
        )
        rateList = RateList(
                TEST_CURRENCY,
                today,
                listOf(Rate("EUR", 0.0130f), Rate("USD", 0.0148f))
        )
    }

    @Test
    fun getRates_returnRatesFromApi_whenApiRequestIsSuccess() {
        whenever(ratesApi.getRates(TEST_CURRENCY)).thenReturn(Single.just(rateListResponse))
        whenever(mapper.transform(rateListResponse)).thenReturn(rateList)

        val testObserver = dataSource.getRates(TEST_CURRENCY).test()

        testObserver.assertComplete().assertValue(rateList)
        verify(mapper).transform(rateListResponse)
    }

    @Test
    fun getRates_returnError_whenApiRequestIsFailed() {
        whenever(ratesApi.getRates(TEST_CURRENCY)).thenReturn(Single.error(RuntimeException()))

        val testObserver = dataSource.getRates(TEST_CURRENCY).test()

        testObserver.assertNotComplete().assertError(RuntimeException::class.java)
        verifyNoMoreInteractions(mapper)
    }
}