
package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header.artworks

import androidx.compose.runtime.Immutable

private const val DEFAULT_IMAGE_ID = "default_image_id"

@Immutable
internal sealed class GameInfoArtworkUiModel {
    data object DefaultImage : GameInfoArtworkUiModel()
    data class UrlImage(val id: String, val url: String) : GameInfoArtworkUiModel()
}

internal val GameInfoArtworkUiModel.id: String
    get() = when (this) {
        is GameInfoArtworkUiModel.DefaultImage -> DEFAULT_IMAGE_ID
        is GameInfoArtworkUiModel.UrlImage -> id
    }
