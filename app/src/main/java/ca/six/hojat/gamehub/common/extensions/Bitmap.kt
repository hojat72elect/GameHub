package ca.six.hojat.gamehub.common.extensions

import android.graphics.Bitmap
import android.graphics.Color


val Bitmap.centerX: Float
    get() = (width / 2f)

val Bitmap.centerY: Float
    get() = (height / 2f)

/**
 * Checks whether the bitmap has any transparent pixels.
 *
 * Note: It is not desirable to invoke this method on the
 * main thread since it may take some time to finish.
 */
fun Bitmap.hasTransparentPixels(): Boolean {
    for (x in 0 until width) {
        for (y in 0 until height) {
            if (getPixel(x, y) == Color.TRANSPARENT) {
                return true
            }
        }
    }

    return false
}

