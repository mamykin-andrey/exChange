package ru.mamykin.exchange.core.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.mamykin.exchange.BuildConfig
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

internal class OkHttpClientProvider @Inject constructor() : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return httpClientBuilder.build()
    }
}