package ru.mamykin.exchange.data.network.rates

import com.google.gson.annotations.SerializedName
import java.util.*

data class RateListResponse(
        @SerializedName("base")
        val base: String,
        @SerializedName("date")
        val date: Date,
        @SerializedName("rates")
        val rates: HashMap<String, Float>
)