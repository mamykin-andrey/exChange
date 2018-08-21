package ru.mamykin.exchange.data.repository

import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.data.repository.datasource.RatesDataSourceFactory
import ru.mamykin.exchange.data.repository.datasource.RatesDataSourceFactory.DataSourceType.Local
import ru.mamykin.exchange.data.repository.datasource.RatesDataSourceFactory.DataSourceType.Remote
import ru.mamykin.exchange.domain.entity.RateList
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

    lateinit var rateList: RateList

    lateinit var repository: RatesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = RatesRepository(dataSourceFactory)
        rateList = RateList(RUB_CURRENCY, Date(), listOf())
        whenever(dataSourceFactory.create(Local)).thenReturn(localDataSource)
        whenever(dataSourceFactory.create(Remote)).thenReturn(remoteDataSource)
        whenever(dataSourceFactory.create()).thenReturn(localDataSource)
    }

    @Test
    fun getRates_shouldSwitchToRemoteDataSource_whenDataAreEmpty() {
        whenever(localDataSource.getRates(RUB_CURRENCY)).thenReturn(Maybe.empty())
        whenever(remoteDataSource.getRates(RUB_CURRENCY)).thenReturn(Maybe.just(rateList))

        val testObserver = repository.getRates(RUB_CURRENCY).test()

        testObserver.assertNoErrors().assertValue(rateList)
        verify(localDataSource).getRates(RUB_CURRENCY)
        verify(remoteDataSource).getRates(RUB_CURRENCY)
        inOrder(localDataSource, remoteDataSource)
    }

    @Test
    fun getRates_shouldCacheData_whenDataLoadedSucceed() {
        whenever(localDataSource.getRates(RUB_CURRENCY)).thenReturn(Maybe.just(rateList))
        whenever(remoteDataSource.getRates(RUB_CURRENCY))
                .thenReturn(Maybe.just(RateList("USD", Date(), listOf())))

        val testObserver = repository.getRates(RUB_CURRENCY).test()

        testObserver.assertNoErrors().assertValue(rateList)
        verify(localDataSource).cacheRates(rateList)
    }
}