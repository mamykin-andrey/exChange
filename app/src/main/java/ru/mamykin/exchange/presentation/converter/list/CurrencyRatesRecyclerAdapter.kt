package ru.mamykin.exchange.presentation.converter.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.mamykin.exchange.R
import ru.mamykin.exchange.databinding.ItemCurrencyRateBinding
import ru.mamykin.exchange.presentation.converter.viewdata.RateViewData

class CurrencyRatesRecyclerAdapter(
    private val onCurrencyOrAmountChanged: (code: String, amount: Float) -> Unit,
) : ListAdapter<RateViewData, CurrencyRateViewHolder>(
    CurrencyRatesDiffUtilCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency_rate, parent, false)
        val binding = ItemCurrencyRateBinding.bind(itemView)
        return CurrencyRateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        holder.bind(getItem(position), onCurrencyOrAmountChanged)
    }

    override fun onViewRecycled(holder: CurrencyRateViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun onFailedToRecycleView(holder: CurrencyRateViewHolder): Boolean {
        holder.unbind()
        return super.onFailedToRecycleView(holder)
    }

    override fun onViewDetachedFromWindow(holder: CurrencyRateViewHolder) {
        holder.unbind()
        super.onViewDetachedFromWindow(holder)
    }
}