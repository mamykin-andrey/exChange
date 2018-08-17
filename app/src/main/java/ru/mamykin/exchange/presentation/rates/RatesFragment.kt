package ru.mamykin.exchange.presentation.rates

import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.BaseFragment

class RatesFragment : BaseFragment() {

    companion object {
        fun newInstance() = RatesFragment()
    }

    override val layoutId = R.layout.fragment_rates
}