package ru.mamykin.exchange.domain.entity

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class RateTest {

    @Test
    fun getDisplayAmount_returnRoundedAmount_whenAmountWithFractionalPart() {
        val displayAmount = Rate("USD", 100.163891016f).getDisplayAmount()

        assertThat(displayAmount, `is`("100.16"))
    }

    @Test
    fun getDisplayAmount_returnSupplementedAmount_whenAmountIsInteger() {
        val displayAmount = Rate("GBP", 25f).getDisplayAmount()

        assertThat(displayAmount, `is`("25.00"))
    }
}