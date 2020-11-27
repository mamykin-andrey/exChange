package ru.mamykin.exchange.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mamykin.exchange.data.network.model.RateListResponse

interface RatesApi {

    companion object {
        const val BASE_URL = "https://api.exchangeratesapi.io"
    }

    @GET("latest")
    fun getRates(@Query("base") baseCurrency: String): Single<RateListResponse>
}