package com.paulrybitskyi.gamedge.feature.search.presentation

import androidx.compose.runtime.Immutable
import com.paulrybitskyi.gamedge.common.ui.widgets.games.GameUiModel
import com.paulrybitskyi.gamedge.common.ui.widgets.games.GamesUiState

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
