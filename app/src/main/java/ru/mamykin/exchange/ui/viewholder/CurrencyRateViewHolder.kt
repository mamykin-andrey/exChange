package ru.mamykin.exchange.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_currency_rate.view.*
import ru.mamykin.exchange.domain.entity.Rate

class CurrencyRateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(rate: Rate, currencySelectedFunc: (String) -> Unit) {
        itemView.currencyCodeTextView!!.text = rate.code
        itemView.currencyNameTextView!!.text = rate.code
        itemView.exchangeAmountEditText!!.setText(rate.amount.toString())

        itemView.exchangeAmountEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                currencySelectedFunc(rate.code)
            }
        }
    }
}