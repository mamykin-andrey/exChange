package ru.mamykin.exchange.presentation.converter.list

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.extension.onFocusedEvents
import ru.mamykin.exchange.core.extension.parseFloat
import ru.mamykin.exchange.core.extension.textChangedEvents
import ru.mamykin.exchange.core.platform.UiUtils
import ru.mamykin.exchange.databinding.ItemCurrencyRateBinding
import ru.mamykin.exchange.domain.entity.Rate
import java.util.concurrent.TimeUnit

class CurrencyRateViewHolder(
    private val context: Context,
    private val binding: ItemCurrencyRateBinding
) : RecyclerView.ViewHolder(binding.root) {

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

    private fun bindCurrencyTitle(currencyCode: String) = binding.apply {
        textCurrencyCode.text = currencyCode
    }

    private fun bindCurrencySubtitle(currencyCode: String) = binding.apply {
        val stringResId = UiUtils.getStringResId(
            context,
            NAME_PREFIX,
            currencyCode.toLowerCase(),
            DEFAULT_NAME
        )
        textCurrencyName.setText(stringResId)
    }

    private fun bindAmountInput(rate: Rate, currencyOrAmountChangedFunc: (String, Float) -> Unit) = binding.apply {
        editExchangeAmount.apply {
            if (!isFocused) {
                setText(rate.getDisplayAmount())
            }
            onFocusedEvents()
                .subscribeOn(Schedulers.io())
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { currencyOrAmountChangedFunc(rate.code, text.parseFloat()) }
            textChangedEvents()
                .subscribeOn(Schedulers.io())
                .filter(String::isNotBlank)
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { currencyOrAmountChangedFunc(rate.code, it.toFloat()) }
        }
    }

    private fun bindCurrencyIcon(currencyCode: String) = binding.apply {
        val iconResId = UiUtils.getDrawableResId(
            context,
            ICON_PREFIX,
            currencyCode.toLowerCase(),
            DEFAULT_ICON
        )
        Picasso.get()
            .load(iconResId)
            .resize(100, 100)
            .centerInside()
            .into(imageCurrencyIcon)
    }
}