package ru.mamykin.exchange.core.platform

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {

    fun addFragment(@IdRes containerId: Int, fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction().add(containerId, fragment).let {
            if (addToBackStack) {
                it.addToBackStack(null)
            }
            it.commitAllowingStateLoss()
        }
    }
}