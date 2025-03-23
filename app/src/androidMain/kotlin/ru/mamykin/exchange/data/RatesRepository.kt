package ru.mamykin.exchange.data

import io.reactivex.Maybe
import io.reactivex.Single
import ru.mamykin.exchange.data.network.RateListResponse
import ru.mamykin.exchange.data.network.RatesApi
import ru.mamykin.exchange.domain.RateEntity
import javax.inject.Inject

internal class RatesRepository @Inject constructor(
    private val ratesApi: RatesApi,
) {
    private var lastRates: List<RateEntity>? = null

    fun getRates(force: Boolean): Single<List<RateEntity>> {
        return if (force) {
            getRemoteRates()
        } else {
            getCachedRates().switchIfEmpty(getRemoteRates())
        }.toSingle()
    }

    private fun getRemoteRates(): Maybe<List<RateEntity>> {
        return ratesApi.getRates()
            .toMaybe()
            .map(RateListResponse::toDomainModel)
            .doOnSuccess(::cacheRates)
    }

    private fun getCachedRates(): Maybe<List<RateEntity>> {
        return if (lastRates == null) Maybe.empty() else Maybe.just(lastRates)
    }

    private fun cacheRates(rates: List<RateEntity>) {
        lastRates = rates
    }
}