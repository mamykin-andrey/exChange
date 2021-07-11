package ru.mamykin.exchange.domain.entity

import java.math.BigDecimal

data class RateEntity(
    val code: String,
    val amount: Float
) {
    fun getDisplayAmount(): String {
        return BigDecimal(amount.toDouble())
            .setScale(2, BigDecimal.ROUND_HALF_UP)
            .toString()
    }
}