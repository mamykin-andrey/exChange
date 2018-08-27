package ru.mamykin.exchange.data.repository.datasource.local

import io.reactivex.Maybe
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RatesLocalDataSource @Inject constructor() : RatesDataSource {

    private var lastRates: RateList? = null

    override fun getRates(baseCurrency: String): Maybe<RateList> {
        return if (lastRates == null) Maybe.empty() else Maybe.just(lastRates)
    }

    override fun cacheRates(rateList: RateList) {
        lastRates = rateList
    }
}