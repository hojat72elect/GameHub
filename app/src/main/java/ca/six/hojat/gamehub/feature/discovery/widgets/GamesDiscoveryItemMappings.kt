package ca.six.hojat.gamehub.feature.discovery.widgets

import ca.six.hojat.gamehub.common.ui.widgets.categorypreview.GamesCategoryPreviewItemUiModel

internal fun List<GamesDiscoveryItemGameUiModel>.mapToCategoryUiModels(): List<GamesCategoryPreviewItemUiModel> {
    return map {
        GamesCategoryPreviewItemUiModel(
            id = it.id,
            title = it.title,
            coverUrl = it.coverUrl,
        )
    }
}

internal fun GamesCategoryPreviewItemUiModel.mapToDiscoveryUiModel(): GamesDiscoveryItemGameUiModel {
    return GamesDiscoveryItemGameUiModel(
        id = id,
        title = title,
        coverUrl = coverUrl,
    )
}
