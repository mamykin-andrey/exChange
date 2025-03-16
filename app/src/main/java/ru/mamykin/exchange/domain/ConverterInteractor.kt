package ru.mamykin.exchange.domain

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.mamykin.exchange.data.RatesRepository
import ru.mamykin.exchange.presentation.CurrentCurrencyRate
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class ConverterInteractor(
    private val ratesRepository: RatesRepository,
    private val ioScheduler: Scheduler,
) {
    @Inject
    constructor(ratesRepository: RatesRepository) : this(ratesRepository, Schedulers.io())

    companion object {
        private const val API_BASE_CURRENCY_CODE = "EUR" // limitations of the API free plan
        private const val EXCHANGE_UPDATE_PERIOD_MS = 30_000L
    }

    /**
     * @param currentCurrency code and amount of the currency, which is used to calculate the exchange rates for all other currencies
     * @return exchange rates calculated to the current currency, if it's not provided, then EUR will be used
     */
    fun getRates(
        currentCurrency: CurrentCurrencyRate?,
        currencyChanged: Boolean,
    ): Observable<Result<List<RateEntity>>> {
        return Observable.interval(0, EXCHANGE_UPDATE_PERIOD_MS, TimeUnit.SECONDS, ioScheduler)
            .flatMapSingle { ratesRepository.getRates(currencyChanged) }
            .map { calculateExchangeRate(it, currentCurrency?.code, currentCurrency?.amountStr?.toFloat()) }
            .map { moveCurrentCurrencyToTop(it, currentCurrency?.code) }
            .map { Result.success(it) }
            .onErrorReturn { Result.failure(it) }
    }

    private fun calculateExchangeRate(
        rates: List<RateEntity>,
        baseCode: String?,
        baseAmount: Float?,
    ): List<RateEntity> {
        if (baseAmount == null) return rates

        val toApiBaseExchangeRate = 1 / rates.find { it.code == baseCode }!!.amount
        return rates.map { currency ->
            val toCurrencyExchangeRate = rates.find { it.code == currency.code }!!.amount
            if (currency.code == baseCode) currency.copy(amount = baseAmount)
            else currency.copy(amount = toApiBaseExchangeRate * toCurrencyExchangeRate * baseAmount)
        }
    }

    private fun moveCurrentCurrencyToTop(
        rates: List<RateEntity>,
        currentCurrency: String?,
    ): List<RateEntity> {
        val currentCurrency = currentCurrency ?: API_BASE_CURRENCY_CODE

        val currentRate = rates.find { it.code == currentCurrency }
        if (currentRate == null) return rates

        return listOf(currentRate) + rates.filter { it.code != currentCurrency }
    }
}