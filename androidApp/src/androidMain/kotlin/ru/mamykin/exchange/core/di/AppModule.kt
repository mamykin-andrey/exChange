package ru.mamykin.exchange.core.di

import android.content.Context
import ru.mamykin.exchange.data.RatesRepository
import ru.mamykin.exchange.data.network.RatesNetworkClient
import ru.mamykin.exchange.domain.ConverterInteractor
import ru.mamykin.exchange.presentation.ConverterViewModel
import toothpick.config.Module

internal class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
        bind(RatesNetworkClient::class.java).toInstance(RatesNetworkClient())
        bind(RatesRepository::class.java).toProvider(RatesRepositoryProvider::class.java)
        bind(ConverterInteractor::class.java).toProvider(ConverterInteractorProvider::class.java)
        bind(ConverterViewModel::class.java).toProvider(ConverterViewModelProvider::class.java)
    }
}