package ru.mamykin.exchange.data.model.mapper

import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.domain.entity.RateList
import ru.mamykin.exchange.data.model.RateListResponse
import javax.inject.Inject

class RateListResponseToRateListMapper @Inject constructor() {

    fun transform(response: RateListResponse): RateList {
        val rates = response.rates.entries.map { Rate(it.key, it.value) }
        return RateList(response.base, response.date, rates)
    }
}