package ru.mamykin.exchange.presentation.converter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.mamykin.exchange.R
import ru.mamykin.exchange.databinding.ItemCurrencyRateBinding
import ru.mamykin.exchange.domain.entity.Rate

class CurrencyRatesRecyclerAdapter(
    private val context: Context,
    private val currencySelectedFunc: (String, Float) -> Unit
) : ListAdapter<Rate, CurrencyRateViewHolder>(
    CurrencyRatesDiffUtilCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_currency_rate, parent, false)
        val binding = ItemCurrencyRateBinding.bind(itemView)
        return CurrencyRateViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        holder.bind(getItem(position), currencySelectedFunc)
    }

    // TODO: ???
    override fun getItemId(position: Int): Long {
        return getItem(position).code.hashCode().toLong()
    }
}