package ca.six.hojat.gamehub.common.ui.theme

import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

private const val DefaultContentAlpha = 1f

object GamedgeTheme {

    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colors

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.shapes

    val spaces: Spaces
        @Composable
        @ReadOnlyComposable
        get() = LocalSpaces.current
}

@Composable
fun GamedgeTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        CompositionLocalProvider(LocalElevationOverlay provides null) {
            MaterialTheme(
                colors = if (useDarkTheme) darkColors() else lightColors(),
                typography = typography,
                shapes = shapes,
            ) {
                CompositionLocalProvider(LocalContentAlpha provides DefaultContentAlpha) {
                    content()
                }
            }
        }
    }
}
