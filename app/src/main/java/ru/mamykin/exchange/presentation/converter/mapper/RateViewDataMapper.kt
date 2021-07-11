package ru.mamykin.exchange.presentation.converter.mapper

import android.content.Context
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.UiUtils
import ru.mamykin.exchange.domain.entity.RateEntity
import ru.mamykin.exchange.presentation.converter.viewdata.RateViewData
import java.math.BigDecimal
import javax.inject.Inject

class RateViewDataMapper @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val ICON_PREFIX = "cur_icon_"
        private const val DEFAULT_ICON = R.drawable.cur_icon_unknown
    }

    fun transform(entities: List<RateEntity>): List<RateViewData> {
        return entities.map {
            RateViewData(
                it.code,
                mapAmount(it.amount),
                mapIcon(it.code)
            )
        }
    }

    private fun mapAmount(amount: Float): String {
        return BigDecimal(amount.toDouble())
            .setScale(2, BigDecimal.ROUND_HALF_UP)
            .toString()
    }

    private fun mapIcon(currencyCode: String): Int {
        return UiUtils.getDrawableResId(
            context,
            ICON_PREFIX,
            currencyCode.toLowerCase(),
            DEFAULT_ICON
        )
    }
}