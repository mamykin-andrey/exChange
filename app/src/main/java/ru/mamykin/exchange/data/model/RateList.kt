package ru.mamykin.exchange.data.model

import java.util.*

data class RateList(val base: String,
                    val date: Date,
                    val rates: List<Rate>
)