package ru.mamykin.exchange.data.model.mapper

import ru.mamykin.exchange.data.model.Rate
import ru.mamykin.exchange.data.model.RateList
import ru.mamykin.exchange.data.network.rates.RateListResponse
import javax.inject.Inject

class RateListResponseToRateListMapper @Inject constructor() {

    fun transform(response: RateListResponse): RateList {
        val rates = response.rates.entries.map { Rate(it.key, it.value) }
        return RateList(response.base, response.date, rates)
    }
}