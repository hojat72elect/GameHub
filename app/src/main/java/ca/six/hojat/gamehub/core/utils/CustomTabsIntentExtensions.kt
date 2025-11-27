package ca.six.hojat.gamehub.core.utils

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import com.paulrybitskyi.commons.window.anims.WindowAnimations

internal fun CustomTabsIntent.Builder.setAnimations(
    context: Context,
    windowAnimations: WindowAnimations,
): CustomTabsIntent.Builder {
    setStartAnimations(
        context,
        windowAnimations.windowBEnterAnimation,
        windowAnimations.windowAExitAnimation,
    )
    setExitAnimations(
        context,
        windowAnimations.windowAEnterAnimation,
        windowAnimations.windowBExitAnimation,
    )

    return this
}
