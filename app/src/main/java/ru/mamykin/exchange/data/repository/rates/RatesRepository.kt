package ru.mamykin.exchange.data.repository.rates

import ru.mamykin.exchange.data.model.RateList
import rx.Single
import javax.inject.Inject

class RatesRepository @Inject constructor(
        private val dataSource: RatesDataSource
) {
    fun getRates(baseCurrency: String): Single<RateList> {
        return dataSource.getRates(baseCurrency)
    }
}