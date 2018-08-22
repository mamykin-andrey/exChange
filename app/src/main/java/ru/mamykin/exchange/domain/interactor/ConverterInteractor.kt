package ru.mamykin.exchange.domain.interactor

import io.reactivex.Observable
import ru.mamykin.exchange.core.scheduler.SchedulersProvider
import ru.mamykin.exchange.data.repository.RatesRepository
import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.domain.entity.RateList
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConverterInteractor @Inject constructor(
        private val ratesRepository: RatesRepository,
        private val schedulersProvider: SchedulersProvider
) {
    fun getRates(currency: String, amount: Float): Observable<RateList> {
        return Observable.interval(0, 1, TimeUnit.SECONDS, schedulersProvider.io())
                .flatMapSingle { ratesRepository.getRates(currency) }
                .map { calculateExchangeRate(it, amount) }
                .map { addCurrentRateToTopOfList(it, currency, amount) }
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