
package com.paulrybitskyi.gamedge.common.ui.widgets.games

import androidx.compose.runtime.Immutable
import com.paulrybitskyi.gamedge.common.ui.widgets.FiniteUiState

@Immutable
data class GamesUiState(
    val isLoading: Boolean,
    val infoIconId: Int,
    val infoTitle: String,
    val games: List<GameUiModel>,
)

val GamesUiState.finiteUiState: FiniteUiState
    get() = when {
        isInEmptyState -> FiniteUiState.Empty
        isInLoadingState -> FiniteUiState.Loading
        isInSuccessState -> FiniteUiState.Success
        else -> error("Unknown games UI state.")
    }

private val GamesUiState.isInEmptyState: Boolean
    get() = (!isLoading && games.isEmpty())

private val GamesUiState.isInLoadingState: Boolean
    get() = (isLoading && games.isEmpty())

private val GamesUiState.isInSuccessState: Boolean
    get() = games.isNotEmpty()

val GamesUiState.isRefreshing: Boolean
    get() = (isLoading && games.isNotEmpty())

fun GamesUiState.toEmptyState(): GamesUiState {
    return copy(isLoading = false, games = emptyList())
}

fun GamesUiState.toLoadingState(): GamesUiState {
    return copy(isLoading = true)
}

fun GamesUiState.toSuccessState(games: List<GameUiModel>): GamesUiState {
    return copy(isLoading = false, games = games)
}
