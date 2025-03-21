package ru.mamykin.exchange.core.di

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.mamykin.exchange.data.network.RatesApi
import javax.inject.Inject
import javax.inject.Provider

internal class RatesApiProvider @Inject constructor(
    private val okHttpClient: OkHttpClient
) : Provider<RatesApi> {

    override fun get(): RatesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(RatesApi.BASE_URL)
            .build()
            .create(RatesApi::class.java)
    }
}