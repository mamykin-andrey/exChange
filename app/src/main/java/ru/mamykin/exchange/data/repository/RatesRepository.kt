package ru.mamykin.exchange.data.repository

import io.reactivex.Single
import ru.mamykin.exchange.data.repository.datasource.RatesDataSourceFactory
import ru.mamykin.exchange.data.repository.datasource.RatesDataSourceFactory.DataSourceType.Local
import ru.mamykin.exchange.data.repository.datasource.RatesDataSourceFactory.DataSourceType.Remote
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RatesRepository @Inject constructor(
        private val dataSourceFactory: RatesDataSourceFactory
) {
    fun getRates(baseCurrency: String): Single<RateList> {
        return dataSourceFactory.create().getRates(baseCurrency)
                .switchIfEmpty(dataSourceFactory.create(Remote).getRates(baseCurrency))
                .toSingle()
                .doOnSuccess(dataSourceFactory.create(Local)::cacheRates)
    }
}