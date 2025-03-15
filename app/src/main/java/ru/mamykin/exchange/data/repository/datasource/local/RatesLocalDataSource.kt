package ru.mamykin.exchange.data.repository.datasource.local

import io.reactivex.Maybe
import ru.mamykin.exchange.domain.entity.RateEntity
import javax.inject.Inject

class RatesLocalDataSource @Inject constructor() {

    private var lastRates: List<RateEntity>? = null

    fun getRates(): Maybe<List<RateEntity>> {
        return if (lastRates == null) Maybe.empty() else Maybe.just(lastRates)
    }

    fun cacheRates(rates: List<RateEntity>) {
        lastRates = rates
    }
}