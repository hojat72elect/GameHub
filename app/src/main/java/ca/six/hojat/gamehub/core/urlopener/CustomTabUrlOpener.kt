package ca.six.hojat.gamehub.core.urlopener

import android.content.Context
import android.net.Uri
import androidx.annotation.ColorRes
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import ca.six.hojat.gamehub.R
import ca.six.hojat.gamehub.core.providers.CustomTabsProvider
import ca.six.hojat.gamehub.core.utils.attachNewTaskFlagIfNeeded
import ca.six.hojat.gamehub.core.utils.isDarkThemeEnabled
import ca.six.hojat.gamehub.core.utils.setAnimations
import com.paulrybitskyi.commons.ktx.configuration
import com.paulrybitskyi.commons.ktx.getCompatColor
import com.paulrybitskyi.commons.window.anims.WindowAnimations
import javax.inject.Inject

@UrlOpenerKey(UrlOpenerKey.Type.CUSTOM_TAB)
internal class CustomTabUrlOpener @Inject constructor(
    private val customTabsProvider: CustomTabsProvider,
) : UrlOpener {

    override fun openUrl(url: String, context: Context): Boolean {
        // If the context is not activity based, then exit animations
        // won't work.

        return if (customTabsProvider.areCustomTabsSupported()) {
            createCustomTabsIntent(context).launchUrl(context, Uri.parse(url))
            true
        } else {
            false
        }
    }

    private fun createCustomTabsIntent(context: Context): CustomTabsIntent {
        return CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setDefaultColorSchemeParams(createColorSchemeParams(context))
            .setAnimations(context, WindowAnimations.HORIZONTAL_SLIDING_ANIMATIONS)
            .build()
            .apply {
                intent.`package` = customTabsProvider.getCustomTabsPackageName()
                intent.attachNewTaskFlagIfNeeded(context)
            }
    }

    private fun createColorSchemeParams(context: Context): CustomTabColorSchemeParams {
        val browserColors = BrowserColors.create(context)

        return CustomTabColorSchemeParams.Builder()
            .setToolbarColor(context.getCompatColor(browserColors.toolbar))
            .setSecondaryToolbarColor(context.getCompatColor(browserColors.secondaryToolbar))
            .setNavigationBarColor(context.getCompatColor(browserColors.navigationBar))
            .build()
    }

    private data class BrowserColors(
        @ColorRes val toolbar: Int,
        @ColorRes val secondaryToolbar: Int,
        @ColorRes val navigationBar: Int,
    ) {

        companion object {

            fun create(context: Context): BrowserColors {
                return if (context.configuration.isDarkThemeEnabled) {
                    BrowserColors(
                        toolbar = R.color.dark_colorPrimary,
                        secondaryToolbar = R.color.dark_colorSurface,
                        navigationBar = R.color.dark_colorPrimary,
                    )
                } else {
                    BrowserColors(
                        toolbar = R.color.light_colorPrimary,
                        secondaryToolbar = R.color.light_colorSurface,
                        navigationBar = R.color.light_colorPrimary,
                    )
                }
            }
        }
    }
}
