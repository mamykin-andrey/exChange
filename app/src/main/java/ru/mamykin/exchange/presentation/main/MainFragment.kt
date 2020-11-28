package ru.mamykin.exchange.presentation.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.extension.reduceSwipeSensitivity
import ru.mamykin.exchange.core.extension.viewBinding
import ru.mamykin.exchange.core.platform.BaseFragment
import ru.mamykin.exchange.databinding.FragmentMainBinding

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val adapter by lazy { MainViewPagerAdapter(this) }
    private val binding by viewBinding { FragmentMainBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initViewPager()
    }

    private fun initToolbar() = binding.apply {
        toolbar.title = getString(R.string.rates_and_conversions_title)
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close)
        toolbar.setNavigationOnClickListener { requireActivity().finish() }
    }

    private fun initViewPager() = binding.apply {
        viewpager.reduceSwipeSensitivity()
        viewpager.adapter = adapter
        viewpager.currentItem = 1
        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()
    }
}