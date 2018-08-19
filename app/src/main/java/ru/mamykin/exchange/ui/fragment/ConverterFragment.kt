package ru.mamykin.exchange.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_converter.*
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.di.Scopes
import ru.mamykin.exchange.core.platform.BaseFragment
import ru.mamykin.exchange.domain.entity.RateList
import ru.mamykin.exchange.presentation.presenter.ConverterPresenter
import ru.mamykin.exchange.presentation.view.ConverterView
import ru.mamykin.exchange.ui.adapter.CurrencyRatesRecyclerAdapter
import toothpick.Toothpick

class ConverterFragment : BaseFragment(), ConverterView {

    companion object {
        fun newInstance() = ConverterFragment()
    }

    override val layoutId = R.layout.fragment_converter

    @InjectPresenter
    lateinit var presenter: ConverterPresenter

    private lateinit var adapter: CurrencyRatesRecyclerAdapter

    @ProvidePresenter
    fun provideConverterPresenter(): ConverterPresenter {
        return Toothpick.openScopes(Scopes.APP_SCOPE, this)
                .getInstance(ConverterPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CurrencyRatesRecyclerAdapter(presenter::onCurrencyOrAmountChanged)
        ratesRecyclerView.adapter = adapter
        ratesRecyclerView.itemAnimator = null
    }

    override fun onFinish() {
        super.onFinish()
        Toothpick.closeScope(this)
    }

    override fun showRateList(rateList: RateList) {
        adapter.changeCurrencyRates(rateList.rates)
    }

    override fun showLoadingError() {
        Toast.makeText(context, "Error was occured", Toast.LENGTH_LONG).show()
    }
}