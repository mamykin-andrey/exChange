package ru.mamykin.exchange.data.model.mapper

import ru.mamykin.exchange.data.model.RateListResponse
import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RateListResponseToRateListMapper @Inject constructor() {

    fun transform(response: RateListResponse): RateList {
        val rates = response.rates.entries.map { Rate(it.key, it.value) }.sortedBy(Rate::name)
        return RateList(response.base, response.date, rates)
    }
}