package ca.six.hojat.gamehub.feature.info.presentation.widgets.main

import androidx.compose.runtime.Immutable
import ca.six.hojat.gamehub.feature.info.presentation.widgets.companies.GameInfoCompanyUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.details.GameInfoDetailsUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.GameInfoHeaderUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.links.GameInfoLinkUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGamesUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.screenshots.GameInfoScreenshotUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.videos.GameInfoVideoUiModel

@Immutable
internal data class GameInfoUiModel(
    val id: Int,
    val headerModel: GameInfoHeaderUiModel,
    val videoModels: List<GameInfoVideoUiModel>,
    val screenshotModels: List<GameInfoScreenshotUiModel>,
    val summary: String?,
    val detailsModel: GameInfoDetailsUiModel?,
    val linkModels: List<GameInfoLinkUiModel>,
    val companyModels: List<GameInfoCompanyUiModel>,
    val otherCompanyGames: GameInfoRelatedGamesUiModel?,
    val similarGames: GameInfoRelatedGamesUiModel?,
) {

    val hasVideos: Boolean
        get() = videoModels.isNotEmpty()

    val hasScreenshots: Boolean
        get() = screenshotModels.isNotEmpty()

    val hasSummary: Boolean
        get() = ((summary != null) && summary.isNotBlank())

    val hasDetails: Boolean
        get() = (detailsModel != null)

    val hasLinks: Boolean
        get() = linkModels.isNotEmpty()

    val hasCompanies: Boolean
        get() = companyModels.isNotEmpty()

    val hasOtherCompanyGames: Boolean
        get() = ((otherCompanyGames != null) && otherCompanyGames.hasItems)

    val hasSimilarGames: Boolean
        get() = ((similarGames != null) && similarGames.hasItems)
}
