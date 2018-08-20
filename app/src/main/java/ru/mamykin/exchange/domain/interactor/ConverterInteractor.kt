package ru.mamykin.exchange.domain.interactor

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.mamykin.exchange.data.repository.RatesRepository
import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.domain.entity.RateList
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConverterInteractor @Inject constructor(
        private val ratesRepository: RatesRepository
) {
    fun getRates(currencyCode: String, amount: Float): Observable<RateList> {
        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .flatMapSingle { ratesRepository.getRates(currencyCode) }
                .map { calculateExchangeRate(it, amount) }
                .map { addCurrentRateToTopOfList(it, currencyCode, amount) }
                .retry()
    }

    private fun calculateExchangeRate(original: RateList, sourceAmount: Float): RateList {
        return RateList(original.base, original.date, original.rates.map {
            Rate(it.code, sourceAmount * it.amount)
        })
    }

    private fun addCurrentRateToTopOfList(original: RateList,
                                          currencyCode: String,
                                          amount: Float): RateList {
        val currentRate = Rate(currencyCode, amount)
        return RateList(original.base, original.date, listOf(currentRate).plus(original.rates))
    }
}