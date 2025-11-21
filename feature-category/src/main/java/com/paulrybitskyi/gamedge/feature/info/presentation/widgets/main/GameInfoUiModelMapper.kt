package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.main

import com.paulrybitskyi.gamedge.common.domain.games.entities.Game
import com.paulrybitskyi.gamedge.feature.info.domain.entities.GameInfo
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.companies.GameInfoCompanyUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.companies.mapToUiModels
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.details.GameInfoDetailsUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header.GameInfoHeaderUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.links.GameInfoLinkUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGamesUiModel
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames.mappers.GameInfoOtherCompanyGamesUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames.mappers.GameInfoSimilarGamesUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.screenshots.GameInfoScreenshotUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.screenshots.mapToUiModels
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.videos.GameInfoVideoUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.videos.mapToUiModels
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

internal interface GameInfoUiModelMapper {

    fun mapToUiModel(gameInfo: GameInfo): GameInfoUiModel
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
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
