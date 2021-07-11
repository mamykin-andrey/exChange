package ru.mamykin.exchange.data.repository.datasource.remote

import io.reactivex.Maybe
import ru.mamykin.exchange.data.network.RatesApi
import ru.mamykin.exchange.data.network.model.mapper.RateListResponseToRateListMapper
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.domain.entity.RateEntity
import javax.inject.Inject

class RatesRemoteDataSource @Inject constructor(
    private val ratesApi: RatesApi,
    private val mapper: RateListResponseToRateListMapper
) : RatesDataSource {

    override fun getRates(): Maybe<List<RateEntity>> {
        return ratesApi.getRates()
            .toMaybe()
            .map(mapper::transform)
    }

    override fun cacheRates(rates: List<RateEntity>) {
        TODO("Remote DataSource doesn't support cache!")
    }
}