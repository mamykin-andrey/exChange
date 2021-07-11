package ru.mamykin.exchange.presentation.converter.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.mamykin.exchange.R
import ru.mamykin.exchange.databinding.ItemCurrencyRateBinding
import ru.mamykin.exchange.presentation.converter.viewdata.RateViewData

class CurrencyRatesRecyclerAdapter(
    private val focusChanged: (String, Float) -> Unit,
    private val amountChanged: (String, Float) -> Unit
) : ListAdapter<RateViewData, CurrencyRateViewHolder>(
    CurrencyRatesDiffUtilCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency_rate, parent, false)
        val binding = ItemCurrencyRateBinding.bind(itemView)
        return CurrencyRateViewHolder(
            binding,
            { focusChanged(getItem(it).code, 0f) }, // TODO:
            { pos, amount -> amountChanged(getItem(pos).code, amount.toFloat()) }
        )
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // TODO: ???
//    override fun getItemId(position: Int): Long {
//        return getItem(position).code.hashCode().toLong()
//    }
}