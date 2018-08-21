package ru.mamykin.exchange.data.repository.datasource.local

import io.reactivex.Maybe
import ru.mamykin.exchange.data.database.RatesDao
import ru.mamykin.exchange.data.database.model.RateListDbEntity
import ru.mamykin.exchange.data.database.model.mapper.RateListToRateListDbEntityMapper
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RatesLocalDataSource @Inject constructor(
        private val ratesDao: RatesDao,
        private val mapper: RateListToRateListDbEntityMapper
) : RatesDataSource {

    companion object {
        private const val ONE_MINUTE_IN_MILLIS = 60 * 1000
    }

    private var lastCacheTime: Long = 0

    override fun getRates(baseCurrency: String): Maybe<RateList> {
        return if (ratesDao.rowsCount() > 0)
            Maybe.just(mapper.reverseRateList(getRates()))
        else
            Maybe.empty()
    }

    override fun cacheRates(rateList: RateList) {
        val timeNow = System.currentTimeMillis()
        if (isCacheExpired(timeNow)) {
            lastCacheTime = timeNow

            ratesDao.clearRates()
            ratesDao.clearRateList()
            ratesDao.insertRates(mapper.transformRates(rateList.rates))
            ratesDao.insertRateList(mapper.transformRateList(rateList))
        }
    }

    private fun isCacheExpired(timeNow: Long): Boolean =
            (timeNow - lastCacheTime) >= ONE_MINUTE_IN_MILLIS

    private fun getRates(): RateListDbEntity {
        val rates = ratesDao.getRates()
        val rateList = ratesDao.getRateList()
        rateList.rates = rates
        return rateList
    }
}