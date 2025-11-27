package ca.six.hojat.gamehub.feature.likes.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import ca.six.hojat.gamehub.R
import ca.six.hojat.gamehub.common.ui.CommandsHandler
import ca.six.hojat.gamehub.common.ui.DirectionsHandler
import ca.six.hojat.gamehub.common.ui.base.events.Direction
import ca.six.hojat.gamehub.common.ui.theme.GamedgeTheme
import ca.six.hojat.gamehub.common.ui.widgets.games.GameUiModel
import ca.six.hojat.gamehub.common.ui.widgets.games.Games
import ca.six.hojat.gamehub.common.ui.widgets.games.GamesUiState
import ca.six.hojat.gamehub.common.ui.widgets.toolbars.Toolbar

@Composable
fun LikedGamesScreen(
    modifier: Modifier,
    onNavigate: (Direction) -> Unit,
) {
    LikedGamesScreen(
        viewModel = hiltViewModel(),
        modifier = modifier,
        onNavigate = onNavigate,
    )
}

@Composable
private fun LikedGamesScreen(
    viewModel: LikedGamesViewModel,
    modifier: Modifier,
    onNavigate: (Direction) -> Unit,
) {
    CommandsHandler(viewModel = viewModel)
    DirectionsHandler(viewModel = viewModel, onNavigate = onNavigate)
    LikedGamesScreen(
        uiState = viewModel.uiState.collectAsState().value,
        onSearchButtonClicked = viewModel::onSearchButtonClicked,
        onGameClicked = viewModel::onGameClicked,
        onBottomReached = viewModel::onBottomReached,
        modifier = modifier,
    )
}

@Composable
private fun LikedGamesScreen(
    uiState: GamesUiState,
    onSearchButtonClicked: () -> Unit,
    onGameClicked: (GameUiModel) -> Unit,
    onBottomReached: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        contentWindowInsets = WindowInsets.statusBars,
        modifier = modifier,
        topBar = {
            Toolbar(
                title = stringResource(R.string.liked_games_toolbar_title),
                rightButtonIcon = painterResource(R.drawable.magnify),
                onRightButtonClick = onSearchButtonClicked,
            )
        },
    ) { paddingValues ->
        Games(
            uiState = uiState,
            modifier = Modifier.padding(paddingValues),
            onGameClicked = onGameClicked,
            onBottomReached = onBottomReached,
        )
    }
}

@PreviewLightDark
@Composable
private fun LikedGamesScreenPreview() {
    GamedgeTheme {
        LikedGamesScreen(
            uiState = GamesUiState(
                isLoading = false,
                infoIconId = R.drawable.gamepad_variant_outline,
                infoTitle = "No Games\nNo Games",
                games = emptyList(),
            ),
            onSearchButtonClicked = {},
            onGameClicked = {},
            onBottomReached = {},
        )
    }
}
