package ru.mamykin.exchange.data

import ru.mamykin.exchange.data.network.RateListResponse
import ru.mamykin.exchange.data.network.RatesNetworkClient
import ru.mamykin.exchange.domain.RateEntity

class RatesRepository(
    private val ratesNetworkClient: RatesNetworkClient,
) {
    private var lastRates: List<RateEntity>? = null

    suspend fun getRates(force: Boolean): List<RateEntity> {
        return if (force) {
            getRemoteRates()
        } else {
            getCachedRates() ?: getRemoteRates()
        }
    }

    private suspend fun getRemoteRates(): List<RateEntity> {
        return ratesNetworkClient.getRates()
            .let(RateListResponse::toDomainModel)
            .also { cacheRates(it) }
    }

    private fun getCachedRates(): List<RateEntity>? {
        return lastRates
    }

    private fun cacheRates(rates: List<RateEntity>) {
        lastRates = rates
    }
}