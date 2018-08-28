package ru.mamykin.exchange.ui.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_currency_rate.view.*
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.extension.onTouchEvents
import ru.mamykin.exchange.core.extension.textChangedEvents
import ru.mamykin.exchange.core.extension.toFloat
import ru.mamykin.exchange.core.platform.UiUtils
import ru.mamykin.exchange.domain.entity.Rate
import java.util.concurrent.TimeUnit

class CurrencyRateViewHolder(
        private val context: Context,
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private const val ICON_PREFIX = "cur_icon_"
        private const val NAME_PREFIX = "currency_name_"
        private const val DEFAULT_ICON = R.drawable.cur_icon_unknown
        private const val DEFAULT_NAME = R.string.currency_name_unknown
    }

    fun bind(rate: Rate, currencyOrAmountChangedFunc: (String, Float) -> Unit) {
        if (rate.code != itemView.tag) {
            itemView.tag = rate.code
            bindCurrencyTitle(rate.code)
            bindCurrencySubtitle(rate.code)
            bindCurrencyIcon(rate.code)
        }
        bindAmountInput(rate, currencyOrAmountChangedFunc)
    }

    private fun bindCurrencyTitle(currencyCode: String) {
        itemView.currencyCodeTextView!!.text = currencyCode
    }

    private fun bindCurrencySubtitle(currencyCode: String) {
        val stringResId = UiUtils.getStringResId(
                context,
                NAME_PREFIX,
                currencyCode.toLowerCase(),
                DEFAULT_NAME
        )
        itemView.currencyNameTextView.setText(stringResId)
    }

    private fun bindAmountInput(rate: Rate, currencyOrAmountChangedFunc: (String, Float) -> Unit) {
        itemView.exchangeAmountEditText.apply {
            if (!isFocused) {
                setText(rate.getDisplayAmount())
            }
            onTouchEvents()
                    .subscribeOn(Schedulers.io())
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { currencyOrAmountChangedFunc(rate.code, text.toFloat()) }
            textChangedEvents()
                    .subscribeOn(Schedulers.io())
                    .filter(String::isNotBlank)
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { currencyOrAmountChangedFunc(rate.code, it.toFloat()) }
        }
    }

    private fun bindCurrencyIcon(currencyCode: String) {
        val iconResId = UiUtils.getDrawableResId(
                context,
                ICON_PREFIX,
                currencyCode.toLowerCase(),
                DEFAULT_ICON
        )
        Picasso.with(context)
                .load(iconResId)
                .resize(100, 100)
                .centerInside()
                .into(itemView.currencyIconImageView)
    }
}