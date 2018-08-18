package ru.mamykin.exchange.core.di.module

import android.content.Context
import toothpick.config.Module

class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
    }
}