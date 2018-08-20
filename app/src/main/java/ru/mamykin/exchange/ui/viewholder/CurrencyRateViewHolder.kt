package ru.mamykin.exchange.ui.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_currency_rate.view.*
import ru.mamykin.exchange.BuildConfig
import ru.mamykin.exchange.core.extension.setOnGetFocusListener
import ru.mamykin.exchange.core.extension.textChangedEvents
import ru.mamykin.exchange.core.extension.toFloat
import ru.mamykin.exchange.domain.entity.Rate
import java.util.concurrent.TimeUnit

class CurrencyRateViewHolder(private val context: Context,
                             itemView: View
) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private const val DRAWABLE_TYPE = "drawable"
    }

    fun bind(rate: Rate, currencyOrAmountChangedFunc: (String, Float) -> Unit) {
        bindCurrencyTitle(rate)
        bindCurrencySubtitle(rate)
        bindAmountInput(rate, currencyOrAmountChangedFunc)
        bindCurrencyIcon(rate.code)
    }

    private fun bindCurrencySubtitle(rate: Rate) {
        itemView.currencyNameTextView!!.text = rate.code
    }

    private fun bindCurrencyTitle(rate: Rate) {
        itemView.currencyCodeTextView!!.text = rate.code
    }

    private fun bindAmountInput(rate: Rate, currencyOrAmountChangedFunc: (String, Float) -> Unit) {
        itemView.exchangeAmountEditText.apply {
            if (!isFocused) {
                setText(rate.getDisplayAmount())
            }
            setOnGetFocusListener { currencyOrAmountChangedFunc(rate.code, text.toFloat()) }
            textChangedEvents()
                    .subscribeOn(Schedulers.io())
                    .filter { it.isNotBlank() }
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { currencyOrAmountChangedFunc(rate.code, it.toFloat()) }
        }
    }

    private fun bindCurrencyIcon(currencyCode: String) {
        val iconResId = context.resources.getIdentifier(
                "cur_icon_${currencyCode.toLowerCase()}",
                DRAWABLE_TYPE, BuildConfig.APPLICATION_ID)
        itemView.currencyIconImageView.setImageResource(iconResId)
    }
}