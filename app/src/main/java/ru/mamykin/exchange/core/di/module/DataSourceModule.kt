package ru.mamykin.exchange.core.di.module

import ru.mamykin.exchange.core.di.qualifier.LocalDataSource
import ru.mamykin.exchange.core.di.qualifier.RemoteDataSource
import ru.mamykin.exchange.data.repository.datasource.RatesDataSource
import ru.mamykin.exchange.data.repository.datasource.local.RatesLocalDataSource
import ru.mamykin.exchange.data.repository.datasource.remote.RatesRemoteDataSource
import toothpick.config.Module

class DataSourceModule : Module() {
    init {
        bind(RatesDataSource::class.java)
            .withName(LocalDataSource::class.java)
            .to(RatesLocalDataSource::class.java)

        bind(RatesDataSource::class.java)
            .withName(RemoteDataSource::class.java)
            .to(RatesRemoteDataSource::class.java)
    }
}