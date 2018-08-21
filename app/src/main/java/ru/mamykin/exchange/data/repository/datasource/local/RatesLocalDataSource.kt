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

    override fun getRates(baseCurrency: String): Maybe<RateList> {
        return if (ratesDao.rowsCount() > 0)
            Maybe.just(mapper.reverse(getRates()))
        else
            Maybe.empty()
    }

    override fun cacheRates(rateList: RateList) {
        ratesDao.insert(mapper.transform(rateList))
    }

    private fun getRates(): RateListDbEntity {
        val rates = ratesDao.getRates()
        val rateList = ratesDao.getRateList()
        rateList.rates = rates
        return rateList
    }
}