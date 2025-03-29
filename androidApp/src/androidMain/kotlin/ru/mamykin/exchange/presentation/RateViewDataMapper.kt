package ru.mamykin.exchange.presentation

import android.content.Context
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.getDrawableResId

internal object RateViewDataMapper {

    private const val ICON_PREFIX = "cur_icon_"
    private const val AMOUNT_FORMAT = "%.2f"
    private val defaultIcon = R.drawable.cur_icon_unknown

    fun transform(
        viewData: CurrencyRatesViewData,
        context: Context,
    ): List<AndroidCurrencyRateViewData> {
        val entities = viewData.rates
        val currentCurrencyRate = viewData.currentCurrencyRate
        return entities.map {
            val formattedAmount = if (it.code == currentCurrencyRate?.code)
                currentCurrencyRate.amountStr
            else
                AMOUNT_FORMAT.format(it.amount)
            val selectionPosition = if (it.code == currentCurrencyRate?.code) {
                currentCurrencyRate.selectionPosition
            } else
                null
            AndroidCurrencyRateViewData(
                code = it.code,
                amountStr = formattedAmount,
                icon = mapIcon(it.code, context),
                selectionPosition = selectionPosition,
            )
        }
    }

    private fun mapIcon(currencyCode: String, context: Context): Int {
        return context.getDrawableResId(
            ICON_PREFIX,
            currencyCode.lowercase(),
            defaultIcon
        )
    }
}