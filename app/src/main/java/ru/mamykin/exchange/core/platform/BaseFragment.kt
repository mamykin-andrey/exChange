package ru.mamykin.exchange.core.platform

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(viewLifecycleOwner) { observer(it) }
    }
}