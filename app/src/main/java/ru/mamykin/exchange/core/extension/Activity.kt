package ru.mamykin.exchange.core.extension

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragment(
    @IdRes containerId: Int,
    newInstance: () -> Fragment,
    tag: String,
    addToBackStack: Boolean = true
) {
    val fragment = supportFragmentManager.findFragmentByTag(tag) ?: newInstance()
    val transaction = supportFragmentManager.beginTransaction()
    transaction.add(containerId, fragment)
    transaction.takeIf { addToBackStack }?.addToBackStack(null)
    transaction.commitAllowingStateLoss()
}