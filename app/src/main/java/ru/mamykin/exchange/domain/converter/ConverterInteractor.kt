package ru.mamykin.exchange.domain.converter

import io.reactivex.Observable
import ru.mamykin.exchange.core.Result
import ru.mamykin.exchange.core.rx.SchedulersProvider
import ru.mamykin.exchange.data.repository.RatesRepository
import ru.mamykin.exchange.domain.entity.RateEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConverterInteractor @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val schedulersProvider: SchedulersProvider
) {
    companion object {
        private const val MONEY_DIFF_PRECISION = 0.001f
    }

    fun getRates(currency: String, amount: Float, force: Boolean): Observable<Result<List<RateEntity>>> {
        // TODO: use currency
        return Observable.interval(0, 10_000, TimeUnit.SECONDS, schedulersProvider.io())
            .flatMapSingle { ratesRepository.getRates(force) }
            .map { calculateExchangeRate(it, amount) }
            .map { moveCurrentCurrencyToTop(it, currency) }
            .map { Result.success(it) }
            .onErrorReturn { Result.error(it) }
    }

    private fun calculateExchangeRate(original: List<RateEntity>, sourceAmount: Float): List<RateEntity> {
        // TODO:
        return original
//        return RateList(original.base, original.date, original.rates.map {
//            Rate(it.code, sourceAmount * it.amount)
//        })
    }

    private fun moveCurrentCurrencyToTop(rates: List<RateEntity>, currencyCode: String): List<RateEntity> {
        // TODO:
        return rates
//        val sortedRates = rateList.rates.sortedWith { o1, o2 ->
//            if (o1.code == currencyCode) return@sortedWith -1
//            if (o2.code == currencyCode) return@sortedWith 1
//            return@sortedWith 0
//        }
//        return rateList.copy(rates = sortedRates)
    }

    fun needRecalculate(
        oldCode: String,
        newCode: String,
        oldAmount: Float,
        newAmount: Float
    ): Boolean {
        return oldCode != newCode || Math.abs(oldAmount - newAmount) > MONEY_DIFF_PRECISION
    }
}