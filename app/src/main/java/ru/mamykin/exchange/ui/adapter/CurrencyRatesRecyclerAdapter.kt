package ru.mamykin.exchange.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.mamykin.exchange.R
import ru.mamykin.exchange.domain.entity.Rate
import ru.mamykin.exchange.ui.adapter.diffutil.CurrencyRatesDiffUtilCallback
import ru.mamykin.exchange.ui.viewholder.CurrencyRateViewHolder

class CurrencyRatesRecyclerAdapter(
        private val currencySelectedFunc: (String, Float) -> Unit
) : RecyclerView.Adapter<CurrencyRateViewHolder>() {

    private var rates: List<Rate> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_currency_rate, parent, false)
        return CurrencyRateViewHolder(itemView)
    }

    override fun getItemCount(): Int = rates.count()

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        holder.bind(rates[position], currencySelectedFunc)
    }

    fun changeCurrencyRates(newRates: List<Rate>) {
        this.rates = newRates
        val diffResult = DiffUtil.calculateDiff(CurrencyRatesDiffUtilCallback(rates, newRates))
        diffResult.dispatchUpdatesTo(this)
    }
}