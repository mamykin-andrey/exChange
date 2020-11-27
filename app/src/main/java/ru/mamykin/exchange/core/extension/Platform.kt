package ru.mamykin.exchange.core.extension

import android.text.Editable

fun Editable.parseFloat(): Float = toString().toFloatOrNull() ?: 0f