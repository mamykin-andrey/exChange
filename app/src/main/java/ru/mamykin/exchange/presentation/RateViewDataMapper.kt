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
    }

    private val defaultIcon = R.drawable.cur_icon_unknown

    fun transform(entities: List<RateEntity>, currentCurrencyCode: String?): List<RateViewData> {
        return entities.map {
            RateViewData(
                code = it.code,
                amount = it.amount,
                icon = mapIcon(it.code),
                isCurrent = it.code == currentCurrencyCode,
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