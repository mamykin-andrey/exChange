package ru.mamykin.exchange.data.repository.datasource.local

import io.reactivex.Maybe
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.domain.entity.RateEntity
import javax.inject.Inject

class RatesLocalDataSource @Inject constructor() : RatesDataSource {

    private var lastRates: List<RateEntity>? = null

    override fun getRates(): Maybe<List<RateEntity>> {
        return if (lastRates == null) Maybe.empty() else Maybe.just(lastRates)
    }

    override fun cacheRates(rates: List<RateEntity>) {
        lastRates = rates
    }
}