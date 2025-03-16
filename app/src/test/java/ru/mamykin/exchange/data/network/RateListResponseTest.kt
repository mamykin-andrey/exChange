package ru.mamykin.exchange.data.network

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class RateListResponseTest {

    private val currencies = listOf(
        "RUB" to 77.0f,
        "USD" to 1.14f,
    )

    @Test
    fun `return mapped rates list`() {
        val response = RateListResponse(
            "EUR",
            Date(),
            currencies.toMap(),
        )

        val rateList = response.toDomainModel()

        assertEquals(currencies.size, rateList.size)
        assertEquals(currencies.first(), rateList.first().let { it.code to it.amount })
        assertEquals(currencies[1], rateList[1].let { it.code to it.amount })
    }
}