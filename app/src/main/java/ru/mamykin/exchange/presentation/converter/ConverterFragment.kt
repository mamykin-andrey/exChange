package ru.mamykin.exchange.presentation.converter

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.extension.hideSoftKeyboard
import ru.mamykin.exchange.core.extension.viewBinding
import ru.mamykin.exchange.core.extension.viewModel
import ru.mamykin.exchange.core.platform.BaseFragment
import ru.mamykin.exchange.databinding.FragmentConverterBinding
import ru.mamykin.exchange.presentation.converter.list.CurrencyRatesRecyclerAdapter
import ru.mamykin.exchange.presentation.converter.viewdata.RateViewData
import toothpick.Toothpick
import kotlin.math.abs

class ConverterFragment : BaseFragment(R.layout.fragment_converter) {

    private val viewModel by viewModel<ConverterViewModel>()
    private val binding by viewBinding { FragmentConverterBinding.bind(requireView()) }

    private val adapter by lazy {
        CurrencyRatesRecyclerAdapter(viewModel::onCurrencyOrAmountChanged)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRatesList()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.isLoading.observe { showLoading(it) }
        viewModel.rates.observe { showRates(it) }
        viewModel.error.observe { showError(it) }
        viewModel.currentRateChanged.observe {
            Handler().post {
                binding.rvRates.scrollToPosition(0)
            }
        }
    }

    private fun initRatesList() = binding.apply {
        rvRates.setHasFixedSize(true)
        rvRates.adapter = adapter
        rvRates.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (abs(dy) > 50) {
                    requireContext().hideSoftKeyboard()
                }
            }
        })
    }

    private fun showLoading(show: Boolean) = binding.apply {
        vLoadingError.root.isVisible = false
        rvRates.isVisible = !show
        pbLoading.isVisible = show
    }


    private fun showRates(rates: List<RateViewData>) = binding.apply {
        pbLoading.isVisible = false
        vLoadingError.root.isVisible = false
        rvRates.isVisible = true
        adapter.submitList(rates)
    }

    private fun showError(errorMsg: String) = binding.apply {
        pbLoading.isVisible = false
        rvRates.isVisible = false
        vLoadingError.root.isVisible = true
        vLoadingError.tvErrorMessage.text = errorMsg
        vLoadingError.btnRetry.setOnClickListener { viewModel.startRatesLoading() }
    }

    override fun onStart() {
        super.onStart()
        viewModel.startRatesLoading()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRatesLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toothpick.closeScope(this)
    }
}