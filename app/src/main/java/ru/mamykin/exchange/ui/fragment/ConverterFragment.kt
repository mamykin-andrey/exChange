package ru.mamykin.exchange.ui.fragment

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.di.Scopes
import ru.mamykin.exchange.core.platform.BaseFragment
import ru.mamykin.exchange.presentation.presenter.ConverterPresenter
import ru.mamykin.exchange.presentation.view.ConverterView
import toothpick.Toothpick

class ConverterFragment : BaseFragment(), ConverterView {

    companion object {
        fun newInstance() = ConverterFragment()
    }

    override val layoutId = R.layout.fragment_converter

    @InjectPresenter
    lateinit var presenter: ConverterPresenter

    @ProvidePresenter
    fun provideConverterPresenter(): ConverterPresenter {
        return Toothpick.openScopes(Scopes.APP_SCOPE, this)
                .getInstance(ConverterPresenter::class.java)
    }

    override fun onFinish() {
        super.onFinish()
        Toothpick.closeScope(this)
    }
}