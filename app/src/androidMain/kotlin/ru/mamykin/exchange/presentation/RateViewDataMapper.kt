package ru.mamykin.exchange.presentation

import android.content.Context
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.getDrawableResId
import ru.mamykin.exchange.domain.RateEntity
import javax.inject.Inject

internal class RateViewDataMapper @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val ICON_PREFIX = "cur_icon_"
        private const val AMOUNT_FORMAT = "%.2f"
    }

    private val defaultIcon = R.drawable.cur_icon_unknown

    fun transform(
        entities: List<RateEntity>,
        currentCurrencyRate: CurrentCurrencyRate? = null,
    ): List<CurrencyRateViewData> {
        return entities.map {
            val formattedAmount = if (it.code == currentCurrencyRate?.code)
                currentCurrencyRate.amountStr
            else
                AMOUNT_FORMAT.format(it.amount)
            val selectionPosition = if (it.code == currentCurrencyRate?.code) {
                currentCurrencyRate.selectionPosition
            } else
                null
            CurrencyRateViewData(
                code = it.code,
                amountStr = formattedAmount,
                icon = mapIcon(it.code),
                selectionPosition = selectionPosition,
            )
        }
    }

    private fun mapIcon(currencyCode: String): Int {
        return context.getDrawableResId(
            ICON_PREFIX,
            currencyCode.lowercase(),
            defaultIcon
        )
    }
}