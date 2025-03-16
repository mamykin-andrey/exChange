package ru.mamykin.exchange.data.network

import com.google.gson.annotations.SerializedName
import ru.mamykin.exchange.domain.RateEntity
import java.util.Date

internal data class RateListResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: Date,
    @SerializedName("rates")
    val rates: Map<String, Float>
) {
    fun toDomainModel(): List<RateEntity> {
        return rates.entries.map { RateEntity(it.key, it.value) }
    }
}