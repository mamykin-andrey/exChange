package ru.mamykin.exchange.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_main.*
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.BaseFragment
import ru.mamykin.exchange.presentation.alerts.AlertsFragment
import ru.mamykin.exchange.presentation.converter.ConverterFragment
import ru.mamykin.exchange.presentation.rates.RatesFragment

/**
 * Fragment, which host screens "Rates", "Converter" and "Alerts"
 */
class MainFragment : BaseFragment() {

    companion object {
        private const val CONVERTER_POSITION = 1
    }

    override val layoutId = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewPager()
    }

    private fun setupToolbar() {
        val activity = activity!! as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar!!.title = getString(R.string.rates_and_conversions_title)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener { activity.finish() }
    }

    private fun setupViewPager() {
        val adapter = MainViewPagerAdapter(fragmentManager!!)
        adapter.addItem(RatesFragment.newInstance(), getString(R.string.rates_tab_title))
        adapter.addItem(ConverterFragment.newInstance(), getString(R.string.converter_tab_title))
        adapter.addItem(AlertsFragment.newInstance(), getString(R.string.alerts_tab_title))
        viewpager.adapter = adapter
        viewpager.currentItem = CONVERTER_POSITION
        tabLayout.setupWithViewPager(viewpager)
    }
}