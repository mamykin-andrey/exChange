package ru.mamykin.exchange.data.repository

import io.reactivex.Maybe
import io.reactivex.Single
import ru.mamykin.exchange.data.repository.datasource.local.RatesLocalDataSource
import ru.mamykin.exchange.data.repository.datasource.remote.RatesRemoteDataSource
import ru.mamykin.exchange.domain.entity.RateEntity
import javax.inject.Inject

class RatesRepository @Inject constructor(
    private val remoteDataSource: RatesRemoteDataSource,
    private val localDataSource: RatesLocalDataSource,
) {
    fun getRates(force: Boolean): Single<List<RateEntity>> {
        return if (force) {
            getRemoteRates()
        } else {
            localDataSource.getRates().switchIfEmpty(getRemoteRates())
        }.toSingle()
    }

    private fun getRemoteRates(): Maybe<List<RateEntity>> {
        return remoteDataSource.getRates()
            .doOnSuccess(localDataSource::cacheRates)
    }
}