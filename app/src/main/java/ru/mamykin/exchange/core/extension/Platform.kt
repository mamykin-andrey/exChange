package ru.mamykin.exchange.core.extension

import android.text.Editable

fun Editable.toFloat(): Float {
    return try {
        this.toString().toFloat()
    } catch (e: NumberFormatException) {
        0f
    }
}