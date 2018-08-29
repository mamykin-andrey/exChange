package ru.mamykin.exchange.data.repository.datasource

import ru.mamykin.exchange.core.di.qualifier.LocalDataSource
import ru.mamykin.exchange.core.di.qualifier.RemoteDataSource
import javax.inject.Inject

class RatesDataSourceFactory @Inject constructor(
        @RemoteDataSource
        private val remoteDataSource: RatesDataSource,
        @LocalDataSource
        private val localDataSource: RatesDataSource
) {
    fun create(force: Boolean) = when (force) {
        true -> remoteDataSource
        false -> localDataSource
    }

    fun createRemoteDataSource(): RatesDataSource = remoteDataSource

    fun createLocalDataSource(): RatesDataSource = localDataSource
}