package ru.mamykin.exchange.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import ru.mamykin.exchange.domain.entity.RateList

interface ConverterView : MvpView {

    @AddToEnd
    fun showLoading(show: Boolean)

    @AddToEnd
    fun showRateList(rateList: RateList)
}