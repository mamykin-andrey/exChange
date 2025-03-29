package ru.mamykin.exchange.core.di

import ru.mamykin.exchange.data.RatesRepository
import ru.mamykin.exchange.domain.ConverterInteractor
import javax.inject.Inject
import javax.inject.Provider

internal class ConverterInteractorProvider @Inject constructor(
    private val ratesRepository: RatesRepository,
) : Provider<ConverterInteractor> {

    override fun get() = ConverterInteractor(ratesRepository)
}