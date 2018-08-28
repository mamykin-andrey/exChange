package ru.mamykin.exchange.data.repository

import io.reactivex.Single
import ru.mamykin.exchange.data.repository.datasource.RatesDataSourceFactory
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RatesRepository @Inject constructor(
        private val dataSourceFactory: RatesDataSourceFactory
) {
    fun getRates(baseCurrency: String, force: Boolean): Single<RateList> {
        return dataSourceFactory.create(force).getRates(baseCurrency)
                .switchIfEmpty(dataSourceFactory.createRemoteDataSource().getRates(baseCurrency))
                .doOnSuccess(dataSourceFactory.createLocalDataSource()::cacheRates)
                .toSingle()
    }
}