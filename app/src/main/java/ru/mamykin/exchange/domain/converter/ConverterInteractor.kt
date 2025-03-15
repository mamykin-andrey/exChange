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
        private const val API_BASE_CURRENCY_CODE = "EUR" // limitations of the API free plan
    }

    /**
     * @param baseCode code of the currency, which is used to calculate the exchange rates for all other currencies
     * @param baseAmount amount of the currency, which is used to calculate the exchange rates for all other currencies
     * @return exchange rates calculated to the current currency, if it's not provided, then EUR will be used
     */
    // TODO: Replace with CurrencyRate model
    fun getRates(
        baseCode: String?,
        baseAmount: Float?,
        currencyChanged: Boolean
    ): Observable<Result<List<RateEntity>>> {
        return Observable.interval(0, 10_000, TimeUnit.SECONDS, schedulersProvider.io())
            .flatMapSingle { ratesRepository.getRates(currencyChanged) }
            .map { calculateExchangeRate(it, baseCode, baseAmount) }
            .map { moveCurrentCurrencyToTop(it, baseCode) }
            .map { Result.success(it) }
            .onErrorReturn { Result.error(it) }
    }

    private fun calculateExchangeRate(
        rates: List<RateEntity>,
        baseCode: String?,
        baseAmount: Float?
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