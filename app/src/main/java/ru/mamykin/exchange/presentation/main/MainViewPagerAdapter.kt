package ru.mamykin.exchange.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.mamykin.exchange.R
import ru.mamykin.exchange.presentation.alerts.AlertsFragment
import ru.mamykin.exchange.presentation.converter.ConverterFragment
import ru.mamykin.exchange.presentation.rates.RatesFragment

typealias TitledFragment = Pair<String, () -> Fragment>

class MainViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        TitledFragment(fragment.getString(R.string.rates_tab_title)) { RatesFragment.newInstance() },
        TitledFragment(fragment.getString(R.string.converter_tab_title)) { ConverterFragment.newInstance() },
        TitledFragment(fragment.getString(R.string.alerts_tab_title)) { AlertsFragment.newInstance() }
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments.getOrNull(position)
            ?.second
            ?.invoke()
            ?: throw IllegalArgumentException("Illegal position argument: $position!")
    }

    fun getTitle(position: Int): String {
        return fragments.getOrNull(position)
            ?.first
            ?: throw IllegalArgumentException("Illegal position argument: $position!")
    }
}