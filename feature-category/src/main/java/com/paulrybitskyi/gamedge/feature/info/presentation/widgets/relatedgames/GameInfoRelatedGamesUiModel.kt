
package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames

import androidx.compose.runtime.Immutable

@Immutable
internal data class GameInfoRelatedGamesUiModel(
    val type: GameInfoRelatedGamesType,
    val title: String,
    val items: List<GameInfoRelatedGameUiModel>,
) {

    val hasItems: Boolean
        get() = items.isNotEmpty()
}
