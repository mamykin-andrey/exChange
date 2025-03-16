package ru.mamykin.exchange.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RatesApi {

    companion object {
        const val BASE_URL = "http://api.exchangeratesapi.io/"
    }

    @GET("v1/latest")
    fun getRates(
        @Query("access_key") key: String = "0d6ce55473e8d88bdef781e2212ff0ac",
        @Query("format") format: String = "1",
        @Query("symbols") requestedCurrencyCodes: String = "RUB,EUR,USD,JPY",
    ): Single<RateListResponse>
}