package ru.mamykin.exchange.domain

import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.Result
import javax.inject.Inject

open class ErrorMapper @Inject constructor(
    private val resourceManager: ResourceManager
) {
    open operator fun invoke(throwable: Throwable): String = when (throwable) {
        else -> resourceManager.getString(R.string.error_network)
    }
}