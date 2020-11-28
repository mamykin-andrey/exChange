package ru.mamykin.exchange.presentation.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.BaseFragment

/**
 * Fragment, which host screens "Rates", "Converter" and "Alerts"
 */
class MainFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_main

    private val adapter by lazy { MainViewPagerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initViewPager()
    }

    private fun initToolbar() = toolbar.apply {
        title = getString(R.string.rates_and_conversions_title)
        navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close)
        setNavigationOnClickListener { requireActivity().finish() }
    }

    private fun initViewPager() {
        viewpager.adapter = adapter
        viewpager.currentItem = 1
        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()
    }
}