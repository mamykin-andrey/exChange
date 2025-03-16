package ru.mamykin.exchange.data

import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class RatesRepositoryTest {

    companion object {
        const val RUB_CURRENCY = "RUB"
    }

    @Mock
    lateinit var localDataSource: RatesDataSource
    @Mock
    lateinit var remoteDataSource: RatesDataSource
    @Mock
    lateinit var dataSourceFactory: RatesDataSourceFactory

    lateinit var rateList1: RateList
    lateinit var rateList2: RateList
    lateinit var repository: RatesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = RatesRepository(dataSourceFactory)
        rateList1 = RateList(RUB_CURRENCY, Date(), listOf())
        rateList2 = RateList("USD", Date(), listOf())
        whenever(dataSourceFactory.createLocalDataSource()).thenReturn(localDataSource)
        whenever(dataSourceFactory.createRemoteDataSource()).thenReturn(remoteDataSource)
        whenever(dataSourceFactory.create(true)).thenReturn(remoteDataSource)
        whenever(dataSourceFactory.create(false)).thenReturn(localDataSource)
    }

    @Test
    fun getRates_shouldReturnDataFromRemoteDataSource_whenForceFlagIsTrue() {
        whenever(localDataSource.getRates(RUB_CURRENCY)).thenReturn(Maybe.just(rateList1))
        whenever(remoteDataSource.getRates(RUB_CURRENCY)).thenReturn(Maybe.just(rateList2))

        val testObserver = repository.getRates(true).test()

        testObserver.assertNoErrors().assertValue(rateList2)
        verify(remoteDataSource).getRates(RUB_CURRENCY)
        verify(localDataSource, times(0)).getRates(RUB_CURRENCY)
    }

    @Test
    fun getRates_shouldSwitchToRemoteDataSource_whenForceFlagIsFalse_andLocalDataAreEmpty() {
        whenever(localDataSource.getRates(RUB_CURRENCY)).thenReturn(Maybe.empty())
        whenever(remoteDataSource.getRates(RUB_CURRENCY)).thenReturn(Maybe.just(rateList1))

        val testObserver = repository.getRates(false).test()

        testObserver.assertNoErrors().assertValue(rateList1)
        inOrder(localDataSource, remoteDataSource) {
            verify(localDataSource).getRates(RUB_CURRENCY)
            verify(remoteDataSource).getRates(RUB_CURRENCY)
        }
    }

    @Test
    fun getRates_shouldCacheData_whenDataHasSuccessfulLoaded() {
        whenever(remoteDataSource.getRates(RUB_CURRENCY)).thenReturn(Maybe.just(rateList2))

        val testObserver = repository.getRates(true).test()

        testObserver.assertNoErrors().assertValue(rateList2)
        verify(localDataSource).cacheRates(rateList2)
    }

    // @Test
    //     fun getRates_returnRatesFromApi_whenApiRequestIsSuccess() {
    //         whenever(ratesApi.getRates(TEST_CURRENCY)).thenReturn(Single.just(rateListResponse))
    //         whenever(mapper.transform(rateListResponse)).thenReturn(rateList)
    //
    //         val testObserver = dataSource.getRates(TEST_CURRENCY).test()
    //
    //         testObserver.assertComplete().assertValue(rateList)
    //         verify(mapper).transform(rateListResponse)
    //     }
    //
    //     @Test
    //     fun getRates_returnError_whenApiRequestIsFailed() {
    //         whenever(ratesApi.getRates(TEST_CURRENCY)).thenReturn(Single.error(RuntimeException()))
    //
    //         val testObserver = dataSource.getRates(TEST_CURRENCY).test()
    //
    //         testObserver.assertNotComplete().assertError(RuntimeException::class.java)
    //         verifyNoMoreInteractions(mapper)
    //     }

    // @Test
    //     fun getRates_shouldReturnEmptyItem_inFirstTime() {
    //         val testObserver = dataSource.getRates(RUB_CURRENCY).test()
    //
    //         testObserver.assertNoErrors().assertNoValues()
    //     }
    //
    //     @Test
    //     fun cacheRates_shouldSetLastRatesToNewValue() {
    //         val rateList = RateList(RUB_CURRENCY, Date(), listOf())
    //         dataSource.cacheRates(rateList)
    //
    //         val testObserver = dataSource.getRates(RUB_CURRENCY).test()
    //
    //         testObserver.assertNoErrors().assertValue(rateList)
    //     }
}