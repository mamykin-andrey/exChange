package ru.mamykin.exchange.core.di.module

import ru.mamykin.exchange.presentation.scheduler.AndroidSchedulersProvider
import ru.mamykin.exchange.presentation.scheduler.SchedulersProvider
import toothpick.config.Module

class SchedulersModule : Module() {
    init {
        bind(SchedulersProvider::class.java).to(AndroidSchedulersProvider::class.java)
    }
}