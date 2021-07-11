package ru.mamykin.exchange.data.repository

import io.reactivex.Maybe
import io.reactivex.Single
import ru.mamykin.exchange.data.repository.datasource.RatesDataSourceFactory
import ru.mamykin.exchange.domain.entity.RateEntity
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
    fun getRates(force: Boolean): Single<List<RateEntity>> {
        return dataSourceFactory.create(force)
            .getRates()
            .switchIfEmpty(Maybe.defer {
                dataSourceFactory.createRemoteDataSource().getRates()
            })
            .doOnSuccess(dataSourceFactory.createLocalDataSource()::cacheRates)
            .toSingle()
    }
}