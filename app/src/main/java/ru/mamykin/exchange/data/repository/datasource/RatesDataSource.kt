package ru.mamykin.exchange.data.repository.datasource

import ru.mamykin.exchange.domain.entity.RateList
import rx.Single

interface RatesDataSource {

    fun getRates(baseCurrency: String): Single<RateList>
}