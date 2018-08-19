package ru.mamykin.exchange.presentation.view

import com.arellomobile.mvp.MvpView
import ru.mamykin.exchange.domain.entity.RateList

interface ConverterView : MvpView {

    fun showRateList(rateList: RateList)

    fun showLoadingError()
}