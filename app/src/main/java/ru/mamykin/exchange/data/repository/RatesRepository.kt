package ru.mamykin.exchange.data.repository

import io.reactivex.Maybe
import io.reactivex.Single
import ru.mamykin.exchange.data.repository.datasource.RatesDataSourceFactory
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RatesRepository @Inject constructor(
        private val dataSourceFactory: RatesDataSourceFactory
) {
    /**
     * Get currency exchange rates
     *
     * @param baseCurrency base currency in relation to which courses are considered
     * @param force flag indicating that instead of the cache need to request new data
     */
    fun getRates(baseCurrency: String, force: Boolean): Single<RateList> {
        return dataSourceFactory.create(force).getRates(baseCurrency)
                .switchIfEmpty(Maybe.defer {
                    dataSourceFactory.createRemoteDataSource().getRates(baseCurrency)
                })
                .doOnSuccess(dataSourceFactory.createLocalDataSource()::cacheRates)
                .toSingle()
    }
}