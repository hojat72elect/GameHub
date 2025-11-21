package com.paulrybitskyi.gamedge.feature.news.presentation

import androidx.compose.runtime.Immutable
import com.paulrybitskyi.gamedge.common.ui.widgets.FiniteUiState
import com.paulrybitskyi.gamedge.feature.news.presentation.widgets.GamingNewsItemUiModel

@Immutable
internal data class GamingNewsUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val news: List<GamingNewsItemUiModel> = emptyList(),
)

internal val GamingNewsUiState.finiteUiState: FiniteUiState
    get() = when {
        isInEmptyState -> FiniteUiState.Empty
        isLoading -> FiniteUiState.Loading
        isInSuccessState -> FiniteUiState.Success
        else -> error("Unknown gaming news UI state.")
    }

private val GamingNewsUiState.isInEmptyState: Boolean
    get() = (!isLoading && news.isEmpty())

private val GamingNewsUiState.isInSuccessState: Boolean
    get() = news.isNotEmpty()

internal fun GamingNewsUiState.toEmptyState(): GamingNewsUiState {
    return copy(isLoading = false, news = emptyList())
}

internal fun GamingNewsUiState.toLoadingState(): GamingNewsUiState {
    return copy(isLoading = true)
}

internal fun GamingNewsUiState.toSuccessState(
    news: List<GamingNewsItemUiModel>,
): GamingNewsUiState {
    return copy(isLoading = false, news = news)
}

internal fun GamingNewsUiState.enableRefreshing(): GamingNewsUiState {
    return copy(isRefreshing = true)
}

internal fun GamingNewsUiState.disableRefreshing(): GamingNewsUiState {
    return copy(isRefreshing = false)
}
