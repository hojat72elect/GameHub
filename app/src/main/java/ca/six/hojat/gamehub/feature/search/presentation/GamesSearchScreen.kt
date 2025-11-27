package ca.six.hojat.gamehub.feature.search.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import ca.six.hojat.gamehub.R
import ca.six.hojat.gamehub.common.ui.CommandsHandler
import ca.six.hojat.gamehub.common.ui.DirectionsHandler
import ca.six.hojat.gamehub.common.ui.OnLifecycleEvent
import ca.six.hojat.gamehub.common.ui.base.events.Direction
import ca.six.hojat.gamehub.common.ui.theme.GamedgeTheme
import ca.six.hojat.gamehub.common.ui.widgets.FiniteUiState
import ca.six.hojat.gamehub.common.ui.widgets.games.GameUiModel
import ca.six.hojat.gamehub.common.ui.widgets.games.Games
import ca.six.hojat.gamehub.common.ui.widgets.games.GamesUiState
import ca.six.hojat.gamehub.common.ui.widgets.games.finiteUiState
import ca.six.hojat.gamehub.common.ui.widgets.toolbars.SearchToolbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val KeyboardPopupIntentionalDelay = 300L

@Composable
fun GamesSearchScreen(onNavigate: (Direction) -> Unit) {
    GamesSearchScreen(
        viewModel = hiltViewModel(),
        onNavigate = onNavigate,
    )
}

@Composable
private fun GamesSearchScreen(
    viewModel: GamesSearchViewModel,
    onNavigate: (Direction) -> Unit,
) {
    CommandsHandler(viewModel = viewModel)
    DirectionsHandler(viewModel = viewModel, onNavigate = onNavigate)
    GamesSearchScreen(
        uiState = viewModel.uiState.collectAsState().value,
        onSearchConfirmed = viewModel::onSearchConfirmed,
        onBackButtonClicked = viewModel::onToolbarBackButtonClicked,
        onClearButtonClicked = viewModel::onToolbarClearButtonClicked,
        onQueryChanged = viewModel::onQueryChanged,
        onGameClicked = viewModel::onGameClicked,
        onBottomReached = viewModel::onBottomReached,
    )
}

@Composable
private fun GamesSearchScreen(
    uiState: GamesSearchUiState,
    onSearchConfirmed: (query: String) -> Unit,
    onBackButtonClicked: () -> Unit,
    onClearButtonClicked: () -> Unit,
    onQueryChanged: (query: String) -> Unit,
    onGameClicked: (GameUiModel) -> Unit,
    onBottomReached: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        contentWindowInsets = WindowInsets.statusBars,
        topBar = {
            SearchToolbar(
                queryText = uiState.queryText,
                placeholderText = stringResource(R.string.games_search_toolbar_hint),
                focusRequester = focusRequester,
                onQueryChanged = onQueryChanged,
                onSearchConfirmed = { query ->
                    focusManager.clearFocus(force = true)
                    onSearchConfirmed(query)
                },
                onBackButtonClicked = onBackButtonClicked,
                onClearButtonClicked = onClearButtonClicked,
            )
        },
    ) { paddingValues ->
        Games(
            uiState = uiState.gamesUiState,
            modifier = Modifier.padding(paddingValues),
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            onGameClicked = onGameClicked,
            onBottomReached = onBottomReached,
        )

        OnLifecycleEvent(
            onResume = {
                if (uiState.gamesUiState.finiteUiState == FiniteUiState.Empty) {
                    // On subsequent openings of this screen from the background,
                    // simply calling focusRequester.requestFocus() does not make
                    // the keyboard visible. The workaround is to add small delay
                    // and call keyboardController.show() as well.
                    coroutineScope.launch {
                        delay(KeyboardPopupIntentionalDelay)
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    }
                }
            },
        )
    }
}

@PreviewLightDark
@Composable
private fun GamesSearchScreenSuccessStatePreview() {
    GamedgeTheme {
        GamesSearchScreen(
            uiState = GamesSearchUiState(
                queryText = "God of War",
                gamesUiState = GamesUiState(
                    isLoading = false,
                    infoIconId = R.drawable.magnify,
                    infoTitle = "",
                    games = listOf(
                        GameUiModel(
                            id = 1,
                            coverImageUrl = null,
                            name = "God of War",
                            releaseDate = "Apr 20, 2018 (3 years ago)",
                            developerName = "SIE Santa Monica Studio",
                            description = "Very very very very very very very very very " +
                                    "very very very very very very very very long description",
                        ),
                        GameUiModel(
                            id = 2,
                            coverImageUrl = null,
                            name = "God of War II",
                            releaseDate = "Mar 13, 2007 (14 years ago)",
                            developerName = "SIE Santa Monica Studio",
                            description = "Very very very very very very very very very " +
                                    "very very very very very very very very long description",
                        ),
                        GameUiModel(
                            id = 3,
                            coverImageUrl = null,
                            name = "God of War II HD",
                            releaseDate = "Oct 02, 2010 (11 years ago)",
                            developerName = "Bluepoint Games",
                            description = "Very very very very very very very very very " +
                                    "very very very very very very very very long description",
                        ),
                    ),
                ),
            ),
            onSearchConfirmed = {},
            onBackButtonClicked = {},
            onClearButtonClicked = {},
            onQueryChanged = {},
            onGameClicked = {},
            onBottomReached = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun GamesSearchScreenEmptyStatePreview() {
    GamedgeTheme {
        GamesSearchScreen(
            uiState = GamesSearchUiState(
                queryText = "God of War",
                gamesUiState = GamesUiState(
                    isLoading = false,
                    infoIconId = R.drawable.magnify,
                    infoTitle = "No games found for \n\"God of War\"",
                    games = emptyList(),
                ),
            ),
            onSearchConfirmed = {},
            onBackButtonClicked = {},
            onClearButtonClicked = {},
            onQueryChanged = {},
            onGameClicked = {},
            onBottomReached = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun GamesSearchScreenLoadingStatePreview() {
    GamedgeTheme {
        GamesSearchScreen(
            uiState = GamesSearchUiState(
                queryText = "God of War",
                gamesUiState = GamesUiState(
                    isLoading = true,
                    infoIconId = R.drawable.magnify,
                    infoTitle = "",
                    games = emptyList(),
                ),
            ),
            onSearchConfirmed = {},
            onBackButtonClicked = {},
            onClearButtonClicked = {},
            onQueryChanged = {},
            onGameClicked = {},
            onBottomReached = {},
        )
    }
}
