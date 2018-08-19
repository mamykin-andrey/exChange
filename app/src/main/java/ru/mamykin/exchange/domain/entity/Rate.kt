package ru.mamykin.exchange.domain.entity

import java.math.BigDecimal
import java.math.MathContext

data class Rate(
        val code: String,
        val amount: Float
) {
    companion object {
        const val DISPLAY_ROUND_PLACES = 3
    }

    fun getDisplayAmount(): Float {
        val amountDecimal = BigDecimal(amount.toDouble())
        return amountDecimal.round(MathContext(DISPLAY_ROUND_PLACES)).toFloat()
    }
}