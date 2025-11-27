package ca.six.hojat.gamehub.feature.search.presentation

import androidx.compose.runtime.Immutable
import ca.six.hojat.gamehub.common.ui.widgets.games.GameUiModel
import ca.six.hojat.gamehub.common.ui.widgets.games.GamesUiState

@Immutable
internal data class GamesSearchUiState(
    val queryText: String,
    val gamesUiState: GamesUiState,
)

internal fun GamesUiState.toLoadingState(games: List<GameUiModel>): GamesUiState {
    return copy(
        isLoading = true,
        games = games,
    )
}

internal fun GamesUiState.toSuccessState(
    infoTitle: String,
    games: List<GameUiModel>,
): GamesUiState {
    return copy(
        isLoading = false,
        infoTitle = infoTitle,
        games = games,
    )
}
