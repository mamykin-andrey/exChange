package ru.mamykin.exchange.ui.fragment

import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.BaseFragment
import ru.mamykin.exchange.presentation.view.ConverterView

class ConverterFragment : BaseFragment(), ConverterView {

    companion object {
        fun newInstance() = ConverterFragment()
    }

    override val layoutId = R.layout.fragment_converter
}