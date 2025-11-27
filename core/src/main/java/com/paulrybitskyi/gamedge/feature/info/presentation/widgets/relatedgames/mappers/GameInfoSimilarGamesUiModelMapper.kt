package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames.mappers

import com.paulrybitskyi.gamedge.common.domain.games.entities.Game
import com.paulrybitskyi.gamedge.core.R
import com.paulrybitskyi.gamedge.core.factories.IgdbImageSize
import com.paulrybitskyi.gamedge.core.factories.IgdbImageUrlFactory
import com.paulrybitskyi.gamedge.core.providers.StringProvider
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGameUiModel
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGamesType
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGamesUiModel
import javax.inject.Inject

internal interface GameInfoSimilarGamesUiModelMapper {
    fun mapToUiModel(similarGames: List<Game>): GameInfoRelatedGamesUiModel?
}

internal class GameInfoSimilarGamesUiModelMapperImpl @Inject constructor(
    private val stringProvider: StringProvider,
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
) : GameInfoSimilarGamesUiModelMapper {

    override fun mapToUiModel(similarGames: List<Game>): GameInfoRelatedGamesUiModel? {
        if (similarGames.isEmpty()) return null

        return GameInfoRelatedGamesUiModel(
            type = GameInfoRelatedGamesType.SIMILAR_GAMES,
            title = stringProvider.getString(R.string.game_info_similar_games_title),
            items = similarGames.toRelatedGameUiModels(),
        )
    }

    private fun List<Game>.toRelatedGameUiModels(): List<GameInfoRelatedGameUiModel> {
        return map {
            GameInfoRelatedGameUiModel(
                id = it.id,
                title = it.name,
                coverUrl = it.cover?.let { cover ->
                    igdbImageUrlFactory.createUrl(cover, IgdbImageUrlFactory.Config(IgdbImageSize.BIG_COVER))
                },
            )
        }
    }
}
