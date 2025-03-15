package ru.mamykin.exchange.data.repository.datasource.remote

import io.reactivex.Maybe
import ru.mamykin.exchange.data.network.RatesApi
import ru.mamykin.exchange.data.network.model.mapper.RateListResponseToRateListMapper
import ru.mamykin.exchange.domain.entity.RateEntity
import javax.inject.Inject

class RatesRemoteDataSource @Inject constructor(
    private val ratesApi: RatesApi,
    private val mapper: RateListResponseToRateListMapper
) {
    fun getRates(): Maybe<List<RateEntity>> {
        return ratesApi.getRates()
            .toMaybe()
            .map(mapper::transform)
    }
}