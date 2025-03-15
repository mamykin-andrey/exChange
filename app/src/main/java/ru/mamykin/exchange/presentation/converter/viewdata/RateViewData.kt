package ru.mamykin.exchange.presentation.converter.viewdata

import androidx.annotation.DrawableRes

data class RateViewData(
    val code: String,
    val amount: Float,
    @DrawableRes val icon: Int,
    val isCurrent: Boolean,
)