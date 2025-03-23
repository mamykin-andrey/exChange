package ru.mamykin.exchange.core.di

import android.content.Context
import ru.mamykin.exchange.data.network.RatesNetworkClient
import toothpick.config.Module

internal class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
        bind(RatesNetworkClient::class.java).toInstance(RatesNetworkClient())
    }
}