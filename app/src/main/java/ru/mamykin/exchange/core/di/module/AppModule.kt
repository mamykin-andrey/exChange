package ru.mamykin.exchange.core.di.module

import android.content.Context
import ru.mamykin.exchange.core.platform.ResourceManagerImpl
import ru.mamykin.exchange.domain.ErrorMapper
import ru.mamykin.exchange.domain.ResourceManager
import toothpick.config.Module

class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
        bind(ResourceManager::class.java).to(ResourceManagerImpl::class.java)
        bind(ErrorMapper::class.java)
    }
}