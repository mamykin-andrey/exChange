package ru.mamykin.exchange.core.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import io.reactivex.Observable

fun EditText.onFocusedEvents(): Observable<Boolean> {
    return Observable.create { emitter ->
        this.setOnFocusChangeListener { view, focused ->
            if (focused) emitter.onNext(true)
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

/**
 * Dirty hack - reduce ViewPager2 swipe sensitivity
 */
fun ViewPager2.reduceSwipeSensitivity() {
    val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
    recyclerViewField.isAccessible = true
    val recyclerView = recyclerViewField.get(this) as RecyclerView

    val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
    touchSlopField.isAccessible = true
    val touchSlop = touchSlopField.get(recyclerView) as Int
    touchSlopField.set(recyclerView, touchSlop * 4)
}