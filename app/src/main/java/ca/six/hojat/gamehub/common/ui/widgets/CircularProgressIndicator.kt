package ca.six.hojat.gamehub.common.ui.widgets

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import ca.six.hojat.gamehub.common.ui.theme.GamedgeTheme

@Composable
fun GamedgeProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = GamedgeTheme.colors.secondary,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        strokeWidth = strokeWidth,
    )
}
