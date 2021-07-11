package ru.mamykin.exchange.data.repository.datasource.local

import org.junit.Before
import org.junit.Test
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import java.util.*

class RatesLocalDataSourceTest {

    companion object {
        private const val RUB_CURRENCY = "RUB"
    }

    lateinit var dataSource: RatesDataSource

    @Before
    fun setUp() {
        dataSource = RatesLocalDataSource()
    }

    @Test
    fun getRates_shouldReturnEmptyItem_inFirstTime() {
        val testObserver = dataSource.getRates(RUB_CURRENCY).test()

        testObserver.assertNoErrors().assertNoValues()
    }

    @Test
    fun cacheRates_shouldSetLastRatesToNewValue() {
        val rateList = RateList(RUB_CURRENCY, Date(), listOf())
        dataSource.cacheRates(rateList)

        val testObserver = dataSource.getRates(RUB_CURRENCY).test()

        testObserver.assertNoErrors().assertValue(rateList)
    }
}