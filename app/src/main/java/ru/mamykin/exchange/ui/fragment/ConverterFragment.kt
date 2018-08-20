package ru.mamykin.exchange.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
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
import ru.mamykin.exchange.ui.dialog.ProgressDialogFragment
import toothpick.Toothpick

class ConverterFragment : BaseFragment(), ConverterView {

    companion object {
        private const val PROGRESS_DIALOG_TAG = "progress_dialog_tag"

        fun newInstance() = ConverterFragment()
    }

    override val layoutId = R.layout.fragment_converter

    @InjectPresenter
    lateinit var presenter: ConverterPresenter

    private lateinit var adapter: CurrencyRatesRecyclerAdapter
    private var progressDialog: DialogFragment? = null

    @ProvidePresenter
    fun provideConverterPresenter(): ConverterPresenter {
        return Toothpick.openScopes(Scopes.APP_SCOPE, this)
                .getInstance(ConverterPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRatesAdapter()
    }

    override fun onFinish() {
        super.onFinish()
        Toothpick.closeScope(this)
    }

    override fun showLoading(show: Boolean) {
        progressDialog?.dismiss()
        if (show) {
            progressDialog = ProgressDialogFragment.newInstance()
            progressDialog!!.show(fragmentManager, PROGRESS_DIALOG_TAG)
        }
    }

    override fun showRateList(rateList: RateList) {
        adapter.changeCurrencyRates(rateList.rates)
    }

    override fun showLoadingError() {
        Toast.makeText(context, "Error was occured", Toast.LENGTH_LONG).show()
    }

    private fun initRatesAdapter() {
        adapter = CurrencyRatesRecyclerAdapter(context!!, presenter::onCurrencyOrAmountChanged)
        ratesRecyclerView.adapter = adapter
        ratesRecyclerView.itemAnimator = null
    }
}