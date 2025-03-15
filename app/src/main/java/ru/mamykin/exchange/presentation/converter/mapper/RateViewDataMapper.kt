package ru.mamykin.exchange.presentation.converter.mapper

import android.content.Context
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.UiUtils
import ru.mamykin.exchange.domain.entity.RateEntity
import ru.mamykin.exchange.presentation.converter.viewdata.RateViewData
import javax.inject.Inject

class RateViewDataMapper @Inject constructor(
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
        return UiUtils.getDrawableResId(
            context,
            ICON_PREFIX,
            currencyCode.lowercase(),
            defaultIcon
        )
    }
}