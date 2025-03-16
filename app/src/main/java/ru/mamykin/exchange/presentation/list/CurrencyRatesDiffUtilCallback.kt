package ru.mamykin.exchange.presentation.list

import androidx.recyclerview.widget.DiffUtil
import ru.mamykin.exchange.presentation.CurrencyRateViewData

internal class CurrencyRatesDiffUtilCallback : DiffUtil.ItemCallback<CurrencyRateViewData>() {

    override fun areItemsTheSame(oldItem: CurrencyRateViewData, newItem: CurrencyRateViewData): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: CurrencyRateViewData, newItem: CurrencyRateViewData): Boolean {
        return oldItem == newItem
    }
}