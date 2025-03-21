package ru.mamykin.exchange

import android.app.Application
import ru.mamykin.exchange.core.di.AppModule
import ru.mamykin.exchange.core.di.Scopes
import toothpick.Toothpick
import toothpick.configuration.Configuration

@Suppress("unused")
internal class ConverterApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        }
        val appScope = Toothpick.openScope(Scopes.APP_SCOPE)
        appScope.installModules(AppModule(this))
    }
}