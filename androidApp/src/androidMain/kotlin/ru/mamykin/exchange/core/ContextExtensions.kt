package ru.mamykin.exchange.core

import android.content.Context
import androidx.annotation.DrawableRes
import ru.mamykin.exchange.BuildConfig

private const val DRAWABLE_TYPE = "drawable"

@DrawableRes
internal fun Context.getDrawableResId(
    prefix: String,
    id: String,
    @DrawableRes defaultDrawableId: Int
): Int {
    val drawableResName = prefix + id
    val iconResId = resources.getIdentifier(
        drawableResName, DRAWABLE_TYPE, BuildConfig.APPLICATION_ID
    )
    return if (iconResId != 0) iconResId else defaultDrawableId
}