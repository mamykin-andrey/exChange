package ru.mamykin.exchange.core.di

import ru.mamykin.exchange.data.RatesRepository
import ru.mamykin.exchange.data.network.RatesNetworkClient
import javax.inject.Inject
import javax.inject.Provider

internal class RatesRepositoryProvider @Inject constructor(
    private val ratesNetworkClient: RatesNetworkClient,
) : Provider<RatesRepository> {

    override fun get() = RatesRepository(ratesNetworkClient)
}