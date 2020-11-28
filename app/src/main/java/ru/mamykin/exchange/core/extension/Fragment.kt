package ru.mamykin.exchange.core.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mamykin.exchange.core.di.Scopes
import ru.mamykin.exchange.presentation.converter.ConverterViewModel
import toothpick.Toothpick

inline fun <reified T : ViewModel> Fragment.viewModel() = viewModels<ConverterViewModel> {
    object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return Toothpick.openScopes(Scopes.APP_SCOPE, this@viewModel).getInstance(modelClass)
        }
    }
}