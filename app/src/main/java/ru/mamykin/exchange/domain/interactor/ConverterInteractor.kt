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
    private var currentCurrency: String = ""
    private var currentAmount: Float = 0f

    fun getRates(currency: String?, amount: Float?): Observable<RateList> {
        currency?.let { currentCurrency = currency }
        amount?.let { currentAmount = amount }
        return Observable.interval(0, 1, TimeUnit.SECONDS, schedulersProvider.io())
                .flatMapSingle { ratesRepository.getRates(currentCurrency) }
                .map { calculateExchangeRate(it, currentAmount) }
                .map { addCurrentRateToTopOfList(it, currentCurrency, currentAmount) }
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