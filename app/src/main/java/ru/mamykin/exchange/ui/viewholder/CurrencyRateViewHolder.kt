package ru.mamykin.exchange.ui.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_currency_rate.view.*
import ru.mamykin.exchange.BuildConfig
import ru.mamykin.exchange.R
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
        private const val STRING_TYPE = "string"
    }

    fun bind(rate: Rate, currencyOrAmountChangedFunc: (String, Float) -> Unit) {
        bindCurrencyTitle(rate)
        bindCurrencySubtitle(rate)
        bindAmountInput(rate, currencyOrAmountChangedFunc)
        bindCurrencyIcon(rate.code)
    }

    private fun bindCurrencyTitle(rate: Rate) {
        itemView.currencyCodeTextView!!.text = rate.code
    }

    private fun bindCurrencySubtitle(rate: Rate) {
        val currencyId = "currency_name_${rate.code.toLowerCase()}"
        val stringResId = context.resources.getIdentifier(
                currencyId, STRING_TYPE, BuildConfig.APPLICATION_ID)
        itemView.currencyNameTextView.setText(
                if (stringResId != 0) stringResId else R.string.currency_name_unknown)
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
        val currencyId = "cur_icon_${currencyCode.toLowerCase()}"
        val iconResId = context.resources.getIdentifier(
                currencyId, DRAWABLE_TYPE, BuildConfig.APPLICATION_ID)
        itemView.currencyIconImageView.setImageResource(
                if (iconResId != 0) iconResId else R.drawable.cur_icon_unknown)
    }
}