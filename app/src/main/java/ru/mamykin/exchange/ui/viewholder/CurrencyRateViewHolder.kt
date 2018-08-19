package ru.mamykin.exchange.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_currency_rate.view.*
import ru.mamykin.exchange.domain.entity.Rate

class CurrencyRateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(rate: Rate) {
        itemView.currencyCodeTextView!!.text = rate.name
        itemView.currencyNameTextView!!.text = rate.name
        itemView.exchangeAmountEditText!!.setText(rate.course.toString())
    }
}