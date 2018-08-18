package ru.mamykin.exchange.core.di.provider

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.mamykin.exchange.data.rates.RatesApi
import javax.inject.Inject
import javax.inject.Provider

class RatesApiProvider @Inject constructor(
        private val okHttpClient: OkHttpClient
) : Provider<RatesApi> {

    override fun get(): RatesApi {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(RatesApi.BASE_URL)
                .build()
                .create(RatesApi::class.java)
    }
}