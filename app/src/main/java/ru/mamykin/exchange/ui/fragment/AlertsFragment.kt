package ru.mamykin.exchange.ui.fragment

import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.BaseFragment

/**
 * Alerts screen
 */
class AlertsFragment : BaseFragment() {

    companion object {
        fun newInstance() = AlertsFragment()
    }

    override val layoutId = R.layout.fragment_alerts
}