package ca.six.hojat.gamehub.common.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.material.pullrefresh.PullRefreshDefaults
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ca.six.hojat.gamehub.common.ui.theme.GamedgeTheme

@Composable
fun RefreshableContent(
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    isSwipeEnabled: Boolean = true,
    onRefreshRequested: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefreshRequested ?: {},
        refreshingOffset = PullRefreshDefaults.RefreshingOffset + GamedgeTheme.spaces.spacing_1_5,
    )

    Box(
        modifier = modifier.pullRefresh(
            state = refreshState,
            enabled = isSwipeEnabled,
        ),
    ) {
        content()

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = GamedgeTheme.colors.secondary,
        )
    }
}
