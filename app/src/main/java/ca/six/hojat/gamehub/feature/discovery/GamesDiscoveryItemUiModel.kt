package ca.six.hojat.gamehub.feature.discovery

import androidx.compose.runtime.Immutable
import ca.six.hojat.gamehub.feature.discovery.widgets.GamesDiscoveryItemGameUiModel

@Immutable
internal data class GamesDiscoveryItemUiModel(
    val id: Int,
    val categoryName: String,
    val title: String,
    val isProgressBarVisible: Boolean,
    val games: List<GamesDiscoveryItemGameUiModel>,
)

internal fun List<GamesDiscoveryItemUiModel>.toSuccessState(
    games: List<List<GamesDiscoveryItemGameUiModel>>,
): List<GamesDiscoveryItemUiModel> {
    return mapIndexed { index, itemModel ->
        itemModel.copy(games = games[index])
    }
}

internal fun List<GamesDiscoveryItemUiModel>.showProgressBar(): List<GamesDiscoveryItemUiModel> {
    return map { itemModel -> itemModel.copy(isProgressBarVisible = true) }
}

internal fun List<GamesDiscoveryItemUiModel>.hideProgressBar(): List<GamesDiscoveryItemUiModel> {
    return map { itemModel -> itemModel.copy(isProgressBarVisible = false) }
}
