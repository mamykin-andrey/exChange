package ru.mamykin.exchange.data.repository.datasource

import io.reactivex.Maybe
import ru.mamykin.exchange.domain.entity.RateEntity

interface RatesDataSource {

    fun getRates(): Maybe<List<RateEntity>>

    fun cacheRates(rates: List<RateEntity>)
}