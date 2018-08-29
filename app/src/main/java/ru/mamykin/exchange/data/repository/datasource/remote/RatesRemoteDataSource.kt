package ru.mamykin.exchange.data.repository.datasource.remote

import io.reactivex.Maybe
import ru.mamykin.exchange.core.exception.WrongDataSourceException
import ru.mamykin.exchange.data.network.model.mapper.RateListResponseToRateListMapper
import ru.mamykin.exchange.data.network.RatesApi
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.domain.entity.RateList
import javax.inject.Inject

class RatesRemoteDataSource @Inject constructor(
        private val ratesApi: RatesApi,
        private val mapper: RateListResponseToRateListMapper
) : RatesDataSource {

    override fun getRates(baseCurrency: String): Maybe<RateList> {
        return ratesApi.getRates(baseCurrency).toMaybe().map(mapper::transform)
    }

    override fun cacheRates(rateList: RateList) {
        throw WrongDataSourceException("Remote DataSource cannot perform this operation!")
    }
}