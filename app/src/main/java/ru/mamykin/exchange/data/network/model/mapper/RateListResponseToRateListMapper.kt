package ru.mamykin.exchange.data.network.model.mapper

import ru.mamykin.exchange.data.network.model.RateListResponse
import ru.mamykin.exchange.domain.entity.RateEntity
import javax.inject.Inject

class RateListResponseToRateListMapper @Inject constructor() {

    fun transform(response: RateListResponse): List<RateEntity> {
        return response.rates.entries.map { RateEntity(it.key, it.value) }
    }
}