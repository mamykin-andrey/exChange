package ru.mamykin.exchange.core.platform

import android.content.Context
import androidx.annotation.StringRes
import ru.mamykin.exchange.domain.ResourceManager
import javax.inject.Inject

class ResourceManagerImpl @Inject constructor(
    private val context: Context
) : ResourceManager {

    override fun getString(@StringRes strId: Int): String {
        return context.getString(strId)
    }
}