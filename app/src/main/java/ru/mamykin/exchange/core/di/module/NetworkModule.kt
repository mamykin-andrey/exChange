package ru.mamykin.exchange.core.di.module

import okhttp3.OkHttpClient
import ru.mamykin.exchange.core.di.provider.OkHttpClientProvider
import ru.mamykin.exchange.core.di.provider.RatesApiProvider
import ru.mamykin.exchange.data.network.RatesApi
import toothpick.config.Module

class NetworkModule : Module() {
    init {
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java).singleton()
        bind(RatesApi::class.java).toProvider(RatesApiProvider::class.java).singleton()
    }
}