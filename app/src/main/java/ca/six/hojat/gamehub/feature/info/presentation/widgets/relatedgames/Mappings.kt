package ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames

import ca.six.hojat.gamehub.common.ui.widgets.categorypreview.GamesCategoryPreviewItemUiModel

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
