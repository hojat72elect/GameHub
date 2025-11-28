package ca.six.hojat.gamehub.common.extensions

import android.graphics.Color

private const val COLOR_ALPHA_MAX = 255

val Int.isOpaque: Boolean
    get() = (Color.alpha(this) == COLOR_ALPHA_MAX)