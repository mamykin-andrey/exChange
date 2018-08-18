package ru.mamykin.exchange.data.rates

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface RatesApi {

    companion object {
        const val BASE_URL = "https://revolut.duckdns.org"
    }

    @GET("latest")
    fun getRates(@Query("base") base: String): Observable<RatesResponse>
}