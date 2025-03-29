package ru.mamykin.exchange.presentation

import androidx.annotation.DrawableRes

internal data class AndroidCurrencyRateViewData(
    val code: String,
    val amountStr: String,
    @DrawableRes val icon: Int,
    val selectionPosition: Int? = null,
)