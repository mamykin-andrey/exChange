package ru.mamykin.exchange.presentation

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.di.Scopes
import ru.mamykin.exchange.databinding.ActivityConverterBinding
import ru.mamykin.exchange.presentation.list.CurrencyRatesRecyclerAdapter
import toothpick.Toothpick

internal class ConverterActivity : AppCompatActivity() {

    private val viewModel by lazy {
        Toothpick.openScopes(Scopes.APP_SCOPE, this).getInstance(ConverterViewModel::class.java)
    }
    private lateinit var binding: ActivityConverterBinding
    private val adapter by lazy { CurrencyRatesRecyclerAdapter(viewModel::onCurrencyOrAmountChanged) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        binding = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initRatesList()
        initViewModel()
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
        if (isFinishing) {
            Toothpick.closeScope(this)
        }
    }

    private fun initToolbar() = binding.apply {
        toolbar.title = getString(R.string.rates_and_conversions_title)
        toolbar.navigationIcon = ContextCompat.getDrawable(this@ConverterActivity, R.drawable.ic_close)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initViewModel() {
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.rates.observe(this) { showRates(it) }
        viewModel.error.observe(this) { showError(getString(it)) }
        viewModel.currentRateChanged.observe(this) {
            Handler().post {
                binding.rvRates.scrollToPosition(0)
            }
        }
    }

    private fun initRatesList() = binding.apply {
        rvRates.setHasFixedSize(true)
        rvRates.adapter = adapter
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
}