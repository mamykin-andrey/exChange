package ru.mamykin.exchange.presentation.converter

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.extension.viewBinding
import ru.mamykin.exchange.core.extension.viewModel
import ru.mamykin.exchange.core.platform.BaseFragment
import ru.mamykin.exchange.databinding.FragmentConverterBinding
import ru.mamykin.exchange.domain.entity.RateList
import ru.mamykin.exchange.presentation.converter.list.CurrencyRatesRecyclerAdapter
import toothpick.Toothpick

class ConverterFragment : BaseFragment(R.layout.fragment_converter) {

    private val viewModel by viewModel<ConverterViewModel>()
    private val binding by viewBinding { FragmentConverterBinding.bind(requireView()) }

    private val adapter by lazy {
        CurrencyRatesRecyclerAdapter(requireContext(), ::onCurrencyChanged).apply {
            setHasStableIds(true)
        }
    }

    private fun onCurrencyChanged(code: String, amount: Float) = binding.apply {
        recyclerRates.scrollToPosition(0)
        viewModel.onCurrencyOrAmountChanged(code, amount)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRatesAdapter()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.isLoading.observe { showLoading(it) }
        viewModel.rates.observe { showRates(it) }
    }

    private fun initRatesAdapter() = binding.apply {
        recyclerRates.setHasFixedSize(true)
        recyclerRates.adapter = adapter
    }

    private fun showLoading(show: Boolean) {
        binding.progressLoading.isVisible = show
        binding.recyclerRates.isVisible = !show
    }


    private fun showRates(rateList: RateList) {
        adapter.submitList(rateList.rates)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toothpick.closeScope(this)
    }
}