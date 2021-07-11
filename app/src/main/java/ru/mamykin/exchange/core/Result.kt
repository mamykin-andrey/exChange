package ru.mamykin.exchange.core

sealed class Result<T> {

    data class Success<T>(val value: T) : Result<T>()

    class Error<T>(val throwable: Throwable) : Result<T>()

    companion object {

        fun <T> success(data: T): Result<T> = Success(data)

        fun <T> error(th: Throwable): Result<T> = Error(th)
    }
}