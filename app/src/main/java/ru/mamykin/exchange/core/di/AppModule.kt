package ru.mamykin.exchange.core.di

import android.content.Context
import okhttp3.OkHttpClient
import ru.mamykin.exchange.data.network.RatesApi
import toothpick.config.Module

internal class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java).singleton()
        bind(RatesApi::class.java).toProvider(RatesApiProvider::class.java).singleton()
    }
}