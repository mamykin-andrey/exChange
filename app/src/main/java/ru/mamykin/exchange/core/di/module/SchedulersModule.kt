package ru.mamykin.exchange.core.di.module

import ru.mamykin.exchange.core.scheduler.DefaultSchedulersProvider
import ru.mamykin.exchange.core.scheduler.SchedulersProvider
import toothpick.config.Module

class SchedulersModule : Module() {
    init {
        bind(SchedulersProvider::class.java).to(DefaultSchedulersProvider::class.java)
    }
}