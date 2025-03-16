package ru.mamykin.exchange.presentation.list

import android.text.TextWatcher
import androidx.annotation.DrawableRes
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.mamykin.exchange.databinding.ItemCurrencyRateBinding
import ru.mamykin.exchange.presentation.RateViewData

internal class CurrencyRateViewHolder(
    private val binding: ItemCurrencyRateBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val AMOUNT_FORMAT = "%.2f"
    }

    private var amountTextWatcher: TextWatcher? = null

    fun bind(
        viewData: RateViewData,
        onCurrencyOrAmountChanged: (code: String, amount: Float) -> Unit,
    ) {
        binding.textCurrencyCode.text = viewData.code
        bindAmount(viewData)
        bindCurrencyIcon(viewData.icon)
        subscribeToEvents(viewData, onCurrencyOrAmountChanged)
    }

    fun unbind() {
        binding.editExchangeAmount.onFocusChangeListener = null
        binding.editExchangeAmount.removeTextChangedListener(amountTextWatcher)
        amountTextWatcher = null
    }

    private fun bindAmount(viewData: RateViewData) = binding.apply {
        binding.editExchangeAmount.setText(AMOUNT_FORMAT.format(viewData.amount))
    }

    private fun subscribeToEvents(
        viewData: RateViewData,
        onCurrencyOrAmountChanged: (code: String, amount: Float) -> Unit,
    ) {
        binding.editExchangeAmount.setOnFocusChangeListener { _, focused ->
            if (focused) {
                onCurrencyOrAmountChanged(viewData.code, viewData.amount)
            }
        }
        amountTextWatcher = binding.editExchangeAmount.addTextChangedListener {
            val newText = it.toString()
            if (newText.isNotBlank()) {
                onCurrencyOrAmountChanged(viewData.code, newText.toFloat())
            }
        }
    }

    private fun bindCurrencyIcon(@DrawableRes icon: Int) = binding.apply {
        Picasso.get()
            .load(icon)
            .resize(100, 100)
            .centerInside()
            .into(imageCurrencyIcon)
    }
}