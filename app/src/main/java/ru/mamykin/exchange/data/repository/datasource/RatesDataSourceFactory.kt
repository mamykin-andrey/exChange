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
    private var isFirstRequest = true

    fun create(type: DataSourceType? = null) = when (type) {
        DataSourceType.Local -> localDataSource
        DataSourceType.Remote -> remoteDataSource
        else -> if (isFirstRequest) localDataSource else remoteDataSource
    }

    sealed class DataSourceType {
        object Local : DataSourceType()
        object Remote : DataSourceType()
    }
}