package ca.six.hojat.gamehub

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ca.six.hojat.gamehub.common.domain.common.extensions.execute
import ca.six.hojat.gamehub.common.ui.LocalNetworkStateProvider
import ca.six.hojat.gamehub.common.ui.LocalTextSharer
import ca.six.hojat.gamehub.common.ui.LocalUrlOpener
import ca.six.hojat.gamehub.common.ui.theme.GamedgeTheme
import ca.six.hojat.gamehub.core.providers.NetworkStateProvider
import ca.six.hojat.gamehub.core.sharers.TextSharer
import ca.six.hojat.gamehub.core.urlopener.UrlOpener
import ca.six.hojat.gamehub.feature.settings.domain.entities.Settings
import ca.six.hojat.gamehub.feature.settings.domain.entities.Theme
import ca.six.hojat.gamehub.feature.settings.domain.usecases.ObserveThemeUseCase
import ca.six.hojat.gamehub.shared.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var urlOpener: UrlOpener

    @Inject
    lateinit var textSharer: TextSharer

    @Inject
    lateinit var networkStateProvider: NetworkStateProvider

    @Inject
    lateinit var observeThemeUseCase: ObserveThemeUseCase

    private var shouldKeepSplashOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setupSplashScreen()
        super.onCreate(savedInstanceState)
        setupCompose()
    }

    private fun setupSplashScreen() {
        // Waiting until the app's theme is loaded first before
        // displaying the dashboard to prevent the user from seeing
        // the app blinking as a result of the theme change
        installSplashScreen().setKeepOnScreenCondition(::shouldKeepSplashOpen)
    }

    private fun setupCompose() {
        setContent {
            CompositionLocalsSetup {
                val useDarkTheme = shouldUseDarkTheme()

                EdgeToEdgeHandler(useDarkTheme = useDarkTheme)

                GamedgeTheme(useDarkTheme = useDarkTheme) {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    private fun CompositionLocalsSetup(content: @Composable () -> Unit) {
        CompositionLocalProvider(LocalUrlOpener provides urlOpener) {
            CompositionLocalProvider(LocalTextSharer provides textSharer) {
                CompositionLocalProvider(LocalNetworkStateProvider provides networkStateProvider) {
                    content()
                }
            }
        }
    }

    @Composable
    private fun shouldUseDarkTheme(): Boolean {
        val themeState = observeThemeUseCase.execute().collectAsState(initial = null)
        val theme = (themeState.value ?: Settings.DEFAULT.theme)

        LaunchedEffect(Unit) {
            snapshotFlow { themeState.value }
                .filterNotNull()
                .collect {
                    if (shouldKeepSplashOpen) {
                        shouldKeepSplashOpen = false
                    }
                }
        }

        return when (theme) {
            Theme.LIGHT -> false
            Theme.DARK -> true
            Theme.SYSTEM -> isSystemInDarkTheme()
        }
    }

    @Composable
    private fun EdgeToEdgeHandler(useDarkTheme: Boolean) {
        DisposableEffect(useDarkTheme) {
            // Default behavior, but we override the dark mode detection
            // because we enable a user to change a theme in the settings
            // and it has to be taken into the account here
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim = Color.TRANSPARENT,
                    darkScrim = Color.TRANSPARENT,
                    detectDarkMode = { useDarkTheme },
                ),
                navigationBarStyle = SystemBarStyle.auto(
                    // The default light scrim, as defined by androidx and the platform.
                    // Taken from the EdgeToEdge.kt file.
                    lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF),
                    // The default dark scrim, as defined by androidx and the platform.
                    // Taken from the EdgeToEdge.kt file.
                    darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b),
                    detectDarkMode = { useDarkTheme },
                ),
            )

            onDispose {}
        }
    }
}
