package ru.mamykin.exchange.presentation.converter.list

import androidx.recyclerview.widget.DiffUtil
import ru.mamykin.exchange.domain.entity.Rate

class CurrencyRatesDiffUtilCallback : DiffUtil.ItemCallback<Rate>() {

    override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean {
        return oldItem.code == newItem.code && oldItem.getDisplayAmount() == newItem.getDisplayAmount()
    }
}