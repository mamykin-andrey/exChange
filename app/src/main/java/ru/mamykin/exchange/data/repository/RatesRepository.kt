package ru.mamykin.exchange.data.repository

import io.reactivex.Single
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RatesRepository @Inject constructor(
        private val dataSource: RatesDataSource
) {
    fun getRates(baseCurrency: String): Single<RateList> {
        return dataSource.getRates(baseCurrency)
    }
}