package ru.mamykin.exchange.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_main.*
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.BaseFragment
import ru.mamykin.exchange.ui.adapter.MainViewPagerAdapter

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
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
        activity.supportActionBar!!.title = "Rates & conversions"
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)
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