package ru.mamykin.exchange.core.di.module

import ru.mamykin.exchange.core.di.provider.RatesDaoProvider
import ru.mamykin.exchange.data.database.RatesDao
import toothpick.config.Module

class DatabaseModule() : Module() {
    init {
        bind(RatesDao::class.java).toProvider(RatesDaoProvider::class.java).singletonInScope()
    }
}