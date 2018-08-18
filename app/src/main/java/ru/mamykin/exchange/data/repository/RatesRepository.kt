package ru.mamykin.exchange.data.repository

import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.domain.entity.RateList
import rx.Single
import javax.inject.Inject

class RatesRepository @Inject constructor(
        private val dataSource: RatesDataSource
) {
    fun getRates(baseCurrency: String): Single<RateList> {
        return dataSource.getRates(baseCurrency)
    }
}