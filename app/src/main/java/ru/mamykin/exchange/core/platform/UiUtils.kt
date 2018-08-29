package ru.mamykin.exchange.core.platform

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.mamykin.exchange.BuildConfig

object UiUtils {

    private const val DRAWABLE_TYPE = "drawable"
    private const val STRING_TYPE = "string"

    @DrawableRes
    fun getDrawableResId(context: Context,
                         prefix: String,
                         id: String,
                         @DrawableRes defaultDrawableId: Int
    ): Int {
        val drawableResName = prefix + id
        val iconResId = context.resources.getIdentifier(
                drawableResName, DRAWABLE_TYPE, BuildConfig.APPLICATION_ID)
        return if (iconResId != 0) iconResId else defaultDrawableId
    }

    @StringRes
    fun getStringResId(context: Context,
                       prefix: String,
                       id: String,
                       @StringRes defaultStringId: Int
    ): Int {
        val iconResName = prefix + id
        val stringResId = context.resources.getIdentifier(
                iconResName, STRING_TYPE, BuildConfig.APPLICATION_ID)
        return if (stringResId != 0) stringResId else defaultStringId
    }

    fun addFragment(fragmentManager: FragmentManager,
                    @IdRes containerId: Int,
                    fragment: Fragment,
                    addToBackStack: Boolean = true
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerId, fragment)
        if (addToBackStack)
            transaction.addToBackStack(null)
        transaction.commitAllowingStateLoss()
    }
}