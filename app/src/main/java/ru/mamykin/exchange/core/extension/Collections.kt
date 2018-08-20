package ru.mamykin.exchange.core.extension

fun <T> List<T>.skip(n: Int): List<T> {
    return if (n >= this.size) emptyList() else this.subList(n, this.size)
}