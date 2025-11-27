package ca.six.hojat.gamehub.feature.info.presentation.widgets.header

import androidx.compose.runtime.Immutable
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.artworks.GameInfoArtworkUiModel

@Immutable
internal data class GameInfoHeaderUiModel(
    val artworks: List<GameInfoArtworkUiModel>,
    val isLiked: Boolean,
    val coverImageUrl: String?,
    val title: String,
    val releaseDate: String,
    val developerName: String?,
    val rating: String,
    val likeCount: String,
    val ageRating: String,
    val gameCategory: String,
) {

    val hasCoverImageUrl: Boolean
        get() = (coverImageUrl != null)

    val hasDeveloperName: Boolean
        get() = (developerName != null)
}
