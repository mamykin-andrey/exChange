package ru.mamykin.exchange.data.repository.rates

import ru.mamykin.exchange.data.model.RateList
import rx.Observable

interface RatesDataSource {

    fun getRates(baseCurrency: String): Observable<RateList>
}