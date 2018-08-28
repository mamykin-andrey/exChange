package ru.mamykin.exchange.core.extension

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable

@SuppressLint("ClickableViewAccessibility")
fun EditText.onTouchEvents(): Observable<Boolean> {
    return Observable.create { emitter ->
        this.setOnTouchListener { _, _ ->
            emitter.onNext(true)
            return@setOnTouchListener false
        }
    }
}

fun EditText.textChangedEvents(): Observable<String> {
    return Observable.create { emitter ->
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null && isFocused) {
                    emitter.onNext(p0.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }
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