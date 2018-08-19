package ru.mamykin.exchange.data.repository.datasource

import io.reactivex.Single
import ru.mamykin.exchange.domain.entity.RateList

interface RatesDataSource {

    fun getRates(baseCurrency: String): Single<RateList>
}