package ru.mamykin.exchange.domain.entity

import java.math.BigDecimal

data class Rate(
        val code: String,
        val amount: Float
) {
    fun getDisplayAmount(): String {
        return BigDecimal(amount.toDouble())
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Rate) {
            return false
        }
        return this.code == other.code && (this.amount - other.amount) < 0.01f
    }

    override fun hashCode(): Int {
        var result = code.hashCode()
        result = 31 * result + amount.hashCode()
        return result
    }
}