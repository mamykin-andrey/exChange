package ru.mamykin.exchange.data.rates

import java.util.*

data class RatesResponse(
        val base: String,
        val date: Date,
        val rates: HashMap<String, Float>
)