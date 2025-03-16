package ru.mamykin.exchange.presentation.list

import androidx.recyclerview.widget.DiffUtil
import ru.mamykin.exchange.presentation.RateViewData

internal class CurrencyRatesDiffUtilCallback : DiffUtil.ItemCallback<RateViewData>() {

    override fun areItemsTheSame(oldItem: RateViewData, newItem: RateViewData): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: RateViewData, newItem: RateViewData): Boolean {
        return oldItem == newItem
    }
}