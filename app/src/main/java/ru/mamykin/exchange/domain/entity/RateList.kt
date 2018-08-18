package ru.mamykin.exchange.domain.entity

import java.util.*

data class RateList(val base: String,
                    val date: Date,
                    val rates: List<Rate>
)