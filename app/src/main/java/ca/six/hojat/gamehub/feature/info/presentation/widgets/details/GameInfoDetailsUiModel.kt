package ca.six.hojat.gamehub.feature.info.presentation.widgets.details

import androidx.compose.runtime.Immutable

@Immutable
internal data class GameInfoDetailsUiModel(
    val genresText: String?,
    val platformsText: String?,
    val modesText: String?,
    val playerPerspectivesText: String?,
    val themesText: String?,
) {

    val hasGenresText: Boolean
        get() = (genresText != null)

    val hasPlatformsText: Boolean
        get() = (platformsText != null)

    val hasModesText: Boolean
        get() = (modesText != null)

    val hasPlayerPerspectivesText: Boolean
        get() = (playerPerspectivesText != null)

    val hasThemesText: Boolean
        get() = (themesText != null)
}
