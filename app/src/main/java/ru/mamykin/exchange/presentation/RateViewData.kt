package ru.mamykin.exchange.presentation

import androidx.annotation.DrawableRes

internal data class RateViewData(
    val code: String,
    val amount: Float,
    @DrawableRes val icon: Int,
    val isCurrent: Boolean,
)