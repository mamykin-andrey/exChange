package ru.mamykin.exchange.data.repository.rates

import ru.mamykin.exchange.data.model.RateList
import ru.mamykin.exchange.data.model.mapper.RateListResponseToRateListMapper
import ru.mamykin.exchange.data.network.rates.RatesApi
import rx.Single
import javax.inject.Inject

class RatesRemoteDataSource @Inject constructor(
        private val ratesApi: RatesApi,
        private val mapper: RateListResponseToRateListMapper
) : RatesDataSource {

    override fun getRates(baseCurrency: String): Single<RateList> {
        return ratesApi.getRates(baseCurrency).map(mapper::transform)
    }
}