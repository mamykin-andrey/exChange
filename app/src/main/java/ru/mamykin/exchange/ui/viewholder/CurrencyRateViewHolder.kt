package ru.mamykin.exchange.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_currency_rate.view.*
import ru.mamykin.exchange.core.extension.addOnTextChangedListener
import ru.mamykin.exchange.core.extension.setOnGetFocusListener
import ru.mamykin.exchange.domain.entity.Rate

class CurrencyRateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(rate: Rate, currencyOrAmountChangedFunc: (String, Float) -> Unit) {
        itemView.currencyCodeTextView!!.text = rate.code
        itemView.currencyNameTextView!!.text = rate.code
        itemView.exchangeAmountEditText!!.setText(rate.getDisplayAmount().toString())

        itemView.exchangeAmountEditText.apply {
            setOnGetFocusListener {
                currencyOrAmountChangedFunc(rate.code, text.toString().toFloat())
            }
            addOnTextChangedListener {
                currencyOrAmountChangedFunc(rate.code, text.toString().toFloat())
            }
        }
    }
}