package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames

import com.paulrybitskyi.gamedge.common.ui.widgets.categorypreview.GamesCategoryPreviewItemUiModel

internal fun List<GameInfoRelatedGameUiModel>.mapToCategoryUiModels(): List<GamesCategoryPreviewItemUiModel> {
    return map {
        GamesCategoryPreviewItemUiModel(
            id = it.id,
            title = it.title,
            coverUrl = it.coverUrl,
        )
    }
}

internal fun GamesCategoryPreviewItemUiModel.mapToInfoRelatedGameUiModel(): GameInfoRelatedGameUiModel {
    return GameInfoRelatedGameUiModel(
        id = id,
        title = title,
        coverUrl = coverUrl,
    )
}
