package ru.mamykin.exchange.ui.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.mamykin.exchange.domain.entity.Rate

class CurrencyRatesDiffUtilCallback(
        private val oldRates: List<Rate>,
        private val newRates: List<Rate>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldRates[oldItemPosition].code == newRates[newItemPosition].code

    override fun getOldListSize() = oldRates.size

    override fun getNewListSize() = newRates.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldRates[oldItemPosition] == newRates[newItemPosition]
}