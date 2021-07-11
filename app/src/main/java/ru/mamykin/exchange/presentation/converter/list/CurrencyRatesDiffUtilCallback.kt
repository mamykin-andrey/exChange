package ru.mamykin.exchange.presentation.converter.list

import androidx.recyclerview.widget.DiffUtil
import ru.mamykin.exchange.presentation.converter.ConverterFragment
import ru.mamykin.exchange.presentation.converter.viewdata.RateViewData

class CurrencyRatesDiffUtilCallback : DiffUtil.ItemCallback<RateViewData>() {

    override fun areItemsTheSame(oldItem: RateViewData, newItem: RateViewData): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: RateViewData, newItem: RateViewData): Boolean {
        return oldItem == newItem
    }
}