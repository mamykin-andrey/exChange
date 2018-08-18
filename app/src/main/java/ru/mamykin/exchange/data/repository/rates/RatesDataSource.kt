package ru.mamykin.exchange.data.repository.rates

import ru.mamykin.exchange.data.model.RateList
import rx.Single

interface RatesDataSource {

    fun getRates(baseCurrency: String): Single<RateList>
}