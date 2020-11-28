package ru.mamykin.exchange

import android.app.Application
import ru.mamykin.exchange.core.di.Scopes
import ru.mamykin.exchange.core.di.module.AppModule
import ru.mamykin.exchange.core.di.module.DataSourceModule
import ru.mamykin.exchange.core.di.module.NetworkModule
import ru.mamykin.exchange.core.di.module.SchedulersModule
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator

@Suppress("unused")
class ConverterApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initToothpick()
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().disableReflection())
            FactoryRegistryLocator.setRootRegistry(FactoryRegistry())
            MemberInjectorRegistryLocator.setRootRegistry(MemberInjectorRegistry())
        }
        val appScope = Toothpick.openScope(Scopes.APP_SCOPE)
        appScope.installModules(
            AppModule(this),
            NetworkModule(),
            DataSourceModule(),
            SchedulersModule()
        )
    }
}