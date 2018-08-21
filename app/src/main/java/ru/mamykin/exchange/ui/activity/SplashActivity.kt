package ru.mamykin.exchange.ui.activity

import android.os.Bundle
import ru.mamykin.exchange.core.di.Scopes
import ru.mamykin.exchange.core.platform.BaseActivity
import ru.mamykin.exchange.presentation.presenter.SplashPresenter
import ru.mamykin.exchange.presentation.router.SplashRouter
import ru.mamykin.exchange.ui.navigation.SplashNavigator
import toothpick.Toothpick
import toothpick.config.Module

class SplashActivity : BaseActivity() {

    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        presenter.onFirstViewAttach()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toothpick.closeScope(this)
    }

    private fun createPresenter(): SplashPresenter {
        val splashScreenScope = Toothpick.openScopes(Scopes.APP_SCOPE, this)
        splashScreenScope.installModules(object : Module() {
            init {
                bind(SplashRouter::class.java)
                        .toInstance(SplashNavigator(this@SplashActivity))
            }
        })
        return splashScreenScope.getInstance(SplashPresenter::class.java)
    }
}