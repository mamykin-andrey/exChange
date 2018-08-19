package ru.mamykin.exchange.ui.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.mamykin.exchange.domain.entity.Rate

class CurrencyRatesDiffUtilCallback(
        private val oldRates: List<Rate>,
        private val newRates: List<Rate>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRate = oldRates[oldItemPosition]
        val newRate = newRates[newItemPosition]
        return oldRate.name == newRate.name
    }

    override fun getOldListSize(): Int {
        return oldRates.size
    }

    override fun getNewListSize(): Int {
        return newRates.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRate = oldRates[oldItemPosition]
        val newRate = newRates[newItemPosition]
        return oldRate.name == newRate.name && (oldRate.course - newRate.course) < 0.000000f
    }
}