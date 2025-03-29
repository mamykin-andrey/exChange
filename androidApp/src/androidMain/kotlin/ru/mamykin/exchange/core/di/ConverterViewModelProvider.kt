package ru.mamykin.exchange.core.di

import ru.mamykin.exchange.domain.ConverterInteractor
import ru.mamykin.exchange.presentation.ConverterViewModel
import javax.inject.Inject
import javax.inject.Provider

internal class ConverterViewModelProvider @Inject constructor(
    private val converterInteractor: ConverterInteractor,
) : Provider<ConverterViewModel> {

    override fun get() = ConverterViewModel(converterInteractor)
}