package ru.mamykin.exchange.domain.entity

import java.util.*

/**
 * An entity, which contains list of rates relates to base rate
 */
data class RateList(val base: String,
                    val date: Date,
                    val rates: List<Rate>
)