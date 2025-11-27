package com.paulrybitskyi.gamedge.feature.category

import androidx.compose.runtime.Immutable
import com.paulrybitskyi.gamedge.common.ui.widgets.FiniteUiState

@Immutable
internal data class GamesCategoryUiState(
    val isLoading: Boolean,
    val title: String,
    val games: List<GameCategoryUiModel>,
)

@Immutable
internal data class GameCategoryUiModel(
    val id: Int,
    val title: String,
    val coverUrl: String?,
)

internal val GamesCategoryUiState.finiteUiState: FiniteUiState
    get() = when {
        isInEmptyState -> FiniteUiState.Empty
        isInLoadingState -> FiniteUiState.Loading
        isInSuccessState -> FiniteUiState.Success
        else -> error("Unknown games category UI state.")
    }

private val GamesCategoryUiState.isInEmptyState: Boolean
    get() = (!isLoading && games.isEmpty())

private val GamesCategoryUiState.isInLoadingState: Boolean
    get() = (isLoading && games.isEmpty())

private val GamesCategoryUiState.isInSuccessState: Boolean
    get() = games.isNotEmpty()

internal val GamesCategoryUiState.isRefreshing: Boolean
    get() = (isLoading && games.isNotEmpty())

internal fun GamesCategoryUiState.enableLoading(): GamesCategoryUiState {
    return copy(isLoading = true)
}

internal fun GamesCategoryUiState.disableLoading(): GamesCategoryUiState {
    return copy(isLoading = false)
}

internal fun GamesCategoryUiState.toEmptyState(): GamesCategoryUiState {
    return copy(isLoading = false, games = emptyList())
}

internal fun GamesCategoryUiState.toSuccessState(
    games: List<GameCategoryUiModel>,
): GamesCategoryUiState {
    return copy(isLoading = false, games = games)
}
