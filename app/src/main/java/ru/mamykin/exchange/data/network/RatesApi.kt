package ru.mamykin.exchange.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mamykin.exchange.data.network.model.RateListResponse

interface RatesApi {

    companion object {
        const val BASE_URL = "http://api.exchangeratesapi.io/"
    }

    @GET("v1/latest")
    fun getRates(
        @Query("access_key") key: String = "e5adf722e91b17a2e5394620b5a72a92",
        @Query("format") format: String = "1",
        @Query("symbols") requestedCurrencyCodes: String = "RUB,EUR,USD,JPY",
    ): Single<RateListResponse>
}