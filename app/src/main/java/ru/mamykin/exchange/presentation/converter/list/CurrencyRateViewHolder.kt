package ru.mamykin.exchange.presentation.converter.list

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.mamykin.exchange.core.extension.focusChanges
import ru.mamykin.exchange.core.extension.textChangedEvents
import ru.mamykin.exchange.databinding.ItemCurrencyRateBinding
import ru.mamykin.exchange.presentation.converter.viewdata.RateViewData
import java.util.concurrent.TimeUnit

class CurrencyRateViewHolder(
    private val binding: ItemCurrencyRateBinding,
    private val focusChanged: (Int) -> Unit,
    private val amountChanged: (Int, String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.editExchangeAmount.focusChanges()
            .subscribe {
                if (it) {
                    focusChanged(adapterPosition)
                }
            }

        binding.editExchangeAmount.textChangedEvents()
            .filter(String::isNotBlank)
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribe { amountChanged(adapterPosition, it) }
    }

    fun bind(rate: RateViewData) {
        if (rate.code != itemView.tag) {
            itemView.tag = rate.code
            bindCurrencyTitle(rate.code)
            bindCurrencyIcon(rate.icon)
        }
        bindAmountInput(rate)
    }

    private fun bindCurrencyTitle(currencyCode: String) = binding.apply {
        textCurrencyCode.text = currencyCode
    }

    private fun bindAmountInput(rate: RateViewData) = binding.apply {
        editExchangeAmount.setText(rate.amount)
    }

    private fun bindCurrencyIcon(@DrawableRes icon: Int) = binding.apply {
        Picasso.get()
            .load(icon)
            .resize(100, 100)
            .centerInside()
            .into(imageCurrencyIcon)
    }
}