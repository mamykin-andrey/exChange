package ru.mamykin.exchange.data.network

import kotlinx.serialization.Serializable
import ru.mamykin.exchange.domain.RateEntity

@Serializable
data class RateListResponse(
    val base: String,
    // val date: Date,
    val rates: Map<String, Float>
) {
    fun toDomainModel(): List<RateEntity> {
        return rates.entries.map { RateEntity(it.key, it.value) }
    }
}

// {
//   "success" : true,
//   "timestamp" : 1742733852,
//   "base" : "EUR",
//   "date" : "2025-03-23",
//   "rates" : {
//     "RUB" : 91.924095,
//     "EUR" : 1,
//     "USD" : 1.087725,
//     "JPY" : 162.406522
//   }
// }