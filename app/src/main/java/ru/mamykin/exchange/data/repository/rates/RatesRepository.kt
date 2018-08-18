package ru.mamykin.exchange.data.repository

import ru.mamykin.exchange.data.model.RateList
import ru.mamykin.exchange.data.network.rates.RatesApi
import rx.Observable
import javax.inject.Inject

class RatesRepository @Inject constructor(
        private val api: RatesApi
) {
    fun getRates(baseCurrency: String): Observable<RateList> {
        return api.getRates(baseCurrency)
    }
}