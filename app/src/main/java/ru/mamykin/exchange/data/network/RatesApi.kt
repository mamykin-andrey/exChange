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
        @Query("access_key") key: String = "873c7a26942fbb76c7e15a4e0fffab8e",
        @Query("format") format: String = "1"
    ): Single<RateListResponse>
}