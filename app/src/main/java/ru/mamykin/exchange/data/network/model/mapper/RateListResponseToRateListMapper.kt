package ru.mamykin.exchange.data.network.model.mapper

import ru.mamykin.exchange.data.network.model.RateListResponse
import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RateListResponseToRateListMapper @Inject constructor() {

    fun transform(response: RateListResponse): RateList {
        val rates = response.rates.entries.map { Rate(it.key, it.value) }.sortedBy(Rate::code)
        return RateList(response.base, response.date, rates)
    }
}