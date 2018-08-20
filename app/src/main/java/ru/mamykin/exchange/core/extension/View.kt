package ru.mamykin.exchange.core.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.addOnTextChangedListener(callback: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(p0: Editable?) {
            if (this@addOnTextChangedListener.isFocused) {
                p0?.let { callback(it.toString()) }
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    })
}

fun EditText.setOnGetFocusListener(callback: () -> Unit) {
    this.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            callback()
        }
    }
}

fun Editable.toFloat(): Float {
    return try {
        this.toString().toFloat()
    } catch (e: NumberFormatException) {
        0f
    }
}