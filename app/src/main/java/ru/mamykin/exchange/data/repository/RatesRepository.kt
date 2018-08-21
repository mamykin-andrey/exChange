package ru.mamykin.exchange.data.repository

import io.reactivex.Single
import ru.mamykin.exchange.core.di.qualifier.LocalDataSource
import ru.mamykin.exchange.core.di.qualifier.RemoteDataSource
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RatesRepository @Inject constructor(
        @RemoteDataSource
        private val remoteDataSource: RatesDataSource,
        @LocalDataSource
        private val localDataSource: RatesDataSource
) {
    private var isFirstRequest = true

    fun getRates(baseCurrency: String): Single<RateList> {
        return createDataSource().getRates(baseCurrency)
                .switchIfEmpty(remoteDataSource.getRates(baseCurrency))
                .toSingle()
                .doOnSuccess { localDataSource.cacheRates(it) }
    }

    private fun createDataSource(): RatesDataSource {
        return if (isFirstRequest) localDataSource.also { isFirstRequest = false } else remoteDataSource
    }
}