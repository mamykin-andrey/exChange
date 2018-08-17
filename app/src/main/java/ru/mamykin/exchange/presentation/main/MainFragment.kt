package ru.mamykin.exchange.presentation.main

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_main.*
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.BaseFragment
import ru.mamykin.exchange.presentation.alerts.AlertsFragment
import ru.mamykin.exchange.presentation.converter.ConverterFragment
import ru.mamykin.exchange.presentation.rates.RatesFragment

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override val layoutId = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = MainViewPagerAdapter(fragmentManager!!)
        adapter.addItem(RatesFragment.newInstance(), "Rates")
        adapter.addItem(ConverterFragment.newInstance(), "Converter")
        adapter.addItem(AlertsFragment.newInstance(), "Alerts")
        viewpager.adapter = adapter
        tabLayout.setupWithViewPager(viewpager)
    }
}