package ru.mamykin.exchange.domain

import androidx.annotation.StringRes

interface ResourceManager {

    fun getString(@StringRes strId: Int): String
}