package ca.six.hojat.gamehub.feature.info.presentation.widgets.main

import ca.six.hojat.gamehub.common.domain.games.entities.Game
import ca.six.hojat.gamehub.feature.info.domain.entities.GameInfo
import ca.six.hojat.gamehub.feature.info.presentation.widgets.companies.GameInfoCompanyUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.companies.mapToUiModels
import ca.six.hojat.gamehub.feature.info.presentation.widgets.details.GameInfoDetailsUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.GameInfoHeaderUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.links.GameInfoLinkUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGamesUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.mappers.GameInfoOtherCompanyGamesUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.mappers.GameInfoSimilarGamesUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.screenshots.GameInfoScreenshotUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.screenshots.mapToUiModels
import ca.six.hojat.gamehub.feature.info.presentation.widgets.videos.GameInfoVideoUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.videos.mapToUiModels
import javax.inject.Inject

internal interface GameInfoUiModelMapper {

    fun mapToUiModel(gameInfo: GameInfo): GameInfoUiModel
}

@Suppress("LongParameterList")
internal class GameInfoUiModelMapperImpl @Inject constructor(
    private val headerModelMapper: GameInfoHeaderUiModelMapper,
    private val videoModelMapper: GameInfoVideoUiModelMapper,
    private val screenshotModelMapper: GameInfoScreenshotUiModelMapper,
    private val detailsModelMapper: GameInfoDetailsUiModelMapper,
    private val linkModelMapper: GameInfoLinkUiModelMapper,
    private val companyModelMapper: GameInfoCompanyUiModelMapper,
    private val otherCompanyGamesModelMapper: GameInfoOtherCompanyGamesUiModelMapper,
    private val similarGamesModelMapper: GameInfoSimilarGamesUiModelMapper,
) : GameInfoUiModelMapper {

    override fun mapToUiModel(gameInfo: GameInfo): GameInfoUiModel {
        return GameInfoUiModel(
            id = gameInfo.game.id,
            headerModel = headerModelMapper.mapToUiModel(gameInfo.game, gameInfo.isGameLiked),
            videoModels = videoModelMapper.mapToUiModels(gameInfo.game.videos),
            screenshotModels = screenshotModelMapper.mapToUiModels(gameInfo.game.screenshots),
            summary = gameInfo.game.summary,
            detailsModel = detailsModelMapper.mapToUiModel(gameInfo.game),
            linkModels = linkModelMapper.mapToUiModels(gameInfo.game.websites),
            companyModels = companyModelMapper.mapToUiModels(gameInfo.game.involvedCompanies),
            otherCompanyGames = gameInfo.game.createOtherCompanyGamesUiModel(gameInfo.companyGames),
            similarGames = similarGamesModelMapper.mapToUiModel(gameInfo.similarGames),
        )
    }

    private fun Game.createOtherCompanyGamesUiModel(otherCompanyGames: List<Game>): GameInfoRelatedGamesUiModel? {
        return otherCompanyGamesModelMapper.mapToUiModel(otherCompanyGames, this)
    }
}
