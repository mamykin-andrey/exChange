package ru.mamykin.exchange.data.network.response.mapper

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import ru.mamykin.exchange.data.model.Rate
import ru.mamykin.exchange.data.model.RateList
import ru.mamykin.exchange.data.network.response.RateListResponse
import java.util.*

class RateListResponseToRateListMapperTest {

    private lateinit var mapper: RateListResponseToRateListMapper

    @Before
    fun setUp() {
        mapper = RateListResponseToRateListMapper()
    }

    @Test
    fun transform_returnTransformedRateList_whenRatesNotEmpty() {
        val today = Date()
        val rateListResponse = RateListResponse(
                "EUR",
                today,
                mapOf("RUB" to 77.0f, "USD" to 1.14f)
        )
        val expectedRateList = RateList(
                "EUR",
                today,
                listOf(Rate("RUB", 77.0f), Rate("USD", 1.14f))
        )

        val rateList = mapper.transform(rateListResponse)

        assertThat(rateList, `is`(expectedRateList))
    }

    @Test
    fun transform_returnTransformedRateList_whenRatesIsEmpty() {
        val today = Date()
        val rateListResponse = RateListResponse("EUR", today, mapOf())
        val expectedRateList = RateList("EUR", today, listOf())

        val rateList = mapper.transform(rateListResponse)

        assertThat(rateList, `is`(expectedRateList))
    }
}