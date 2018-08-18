package ru.mamykin.exchange.data.repository.rates

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.mamykin.exchange.data.model.Rate
import ru.mamykin.exchange.data.model.RateList
import ru.mamykin.exchange.data.network.response.mapper.RateListResponseToRateListMapper
import ru.mamykin.exchange.data.network.response.RateListResponse
import ru.mamykin.exchange.data.network.api.RatesApi
import rx.Single
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

    private lateinit var dataSource: RatesDataSource

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

        val testSubscriber = dataSource.getRates(TEST_CURRENCY).test()

        testSubscriber.assertCompleted().assertValue(rateList)
        verify(mapper).transform(rateListResponse)
    }

    @Test
    fun getRates_returnError_whenApiRequestIsFailed() {
        whenever(ratesApi.getRates(TEST_CURRENCY)).thenReturn(Single.error(RuntimeException()))

        val testSubscriber = dataSource.getRates(TEST_CURRENCY).test()

        testSubscriber.assertNotCompleted().assertError(RuntimeException::class.java)
        verifyNoMoreInteractions(mapper)
    }
}