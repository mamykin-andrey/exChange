package ru.mamykin.exchange

interface Platform {
    val platform: String
}

expect fun getPlatform(): Platform