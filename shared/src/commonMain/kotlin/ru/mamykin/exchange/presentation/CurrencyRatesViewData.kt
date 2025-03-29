package ru.mamykin.exchange.presentation

import ru.mamykin.exchange.domain.RateEntity

data class CurrencyRatesViewData(
    val rates: List<RateEntity>,
    val currentCurrencyRate: CurrentCurrencyRate? = null,
)