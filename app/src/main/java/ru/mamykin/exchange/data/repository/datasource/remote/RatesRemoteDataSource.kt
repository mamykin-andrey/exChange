package ru.mamykin.exchange.data.repository.datasource.remote

import io.reactivex.Single
import ru.mamykin.exchange.data.model.mapper.RateListResponseToRateListMapper
import ru.mamykin.exchange.data.network.RatesApi
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RatesRemoteDataSource @Inject constructor(
        private val ratesApi: RatesApi,
        private val mapper: RateListResponseToRateListMapper
) : RatesDataSource {

    override fun getRates(baseCurrency: String): Single<RateList> {
        return ratesApi.getRates(baseCurrency).map(mapper::transform)
    }
}