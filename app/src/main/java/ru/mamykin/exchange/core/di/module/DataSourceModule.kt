package ru.mamykin.exchange.core.di.module

import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.data.repository.datasource.remote.RatesRemoteDataSource
import toothpick.config.Module

class DataSourceModule : Module() {
    init {
        bind(RatesDataSource::class.java).to(RatesRemoteDataSource::class.java)
    }
}