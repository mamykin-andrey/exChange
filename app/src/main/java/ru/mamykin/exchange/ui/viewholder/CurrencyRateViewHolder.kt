package ru.mamykin.exchange.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_currency_rate.view.*
import ru.mamykin.exchange.core.extension.setOnGetFocusListener
import ru.mamykin.exchange.core.extension.textChangedEvents
import ru.mamykin.exchange.core.extension.toFloat
import ru.mamykin.exchange.domain.entity.Rate
import java.util.concurrent.TimeUnit

class CurrencyRateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(rate: Rate, currencyOrAmountChangedFunc: (String, Float) -> Unit) {
        itemView.currencyCodeTextView!!.text = rate.code
        itemView.currencyNameTextView!!.text = rate.code

        itemView.exchangeAmountEditText.apply {
            if (!isFocused) {
                setText(rate.getDisplayAmount())
            }
            setOnGetFocusListener { currencyOrAmountChangedFunc(rate.code, text.toFloat()) }
            // TODO: Maybe move this subscription to presenter?
            textChangedEvents()
                    .subscribeOn(Schedulers.io())
                    .filter { it.isNotBlank() }
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { currencyOrAmountChangedFunc(rate.code, it.toFloat()) }
        }
    }
}