package ru.mamykin.exchange.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : AndroidxMvpFragment() {

    abstract val layoutId: Int

    open fun onFinish() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (activity?.isFinishing == true) {
            onFinish()
        }
    }
}