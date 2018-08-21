package ru.mamykin.exchange.data.repository.datasource

import io.reactivex.Maybe
import ru.mamykin.exchange.domain.entity.RateList

interface RatesDataSource {

    fun getRates(baseCurrency: String): Maybe<RateList>

    fun cacheRates(rateList: RateList)
}