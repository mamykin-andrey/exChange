package ru.mamykin.exchange.domain.entity

import java.math.BigDecimal

/**
 * An entity, which contains information about
 * currency exchange rate - code and amount relates to base currency
 */
data class Rate(
        val code: String,
        val amount: Float
) {
    fun getDisplayAmount(): String {
        return BigDecimal(amount.toDouble())
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .toString()
    }
}