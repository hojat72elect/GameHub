package ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.mappers

import ca.six.hojat.gamehub.R
import ca.six.hojat.gamehub.common.domain.games.entities.Game
import ca.six.hojat.gamehub.core.factories.IgdbImageSize
import ca.six.hojat.gamehub.core.factories.IgdbImageUrlFactory
import ca.six.hojat.gamehub.core.providers.StringProvider
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGameUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGamesType
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGamesUiModel
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
