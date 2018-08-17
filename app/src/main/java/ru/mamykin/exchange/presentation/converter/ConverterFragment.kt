package ru.mamykin.exchange.presentation.converter

import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.BaseFragment

class ConverterFragment : BaseFragment() {

    companion object {
        fun newInstance() = ConverterFragment()
    }

    override val layoutId = R.layout.fragment_converter
}