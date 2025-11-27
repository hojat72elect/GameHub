package ca.six.hojat.gamehub.feature.category.widgets

import ca.six.hojat.gamehub.common.domain.games.entities.Game
import ca.six.hojat.gamehub.core.factories.IgdbImageSize
import ca.six.hojat.gamehub.core.factories.IgdbImageUrlFactory
import ca.six.hojat.gamehub.feature.category.GameCategoryUiModel
import javax.inject.Inject

internal interface GameCategoryUiModelMapper {
    fun mapToUiModel(game: Game): GameCategoryUiModel
}

internal class GameCategoryUiModelMapperImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
) : GameCategoryUiModelMapper {

    override fun mapToUiModel(game: Game): GameCategoryUiModel {
        return GameCategoryUiModel(
            id = game.id,
            title = game.name,
            coverUrl = game.cover?.let { cover ->
                igdbImageUrlFactory.createUrl(cover, IgdbImageUrlFactory.Config(IgdbImageSize.BIG_COVER))
            },
        )
    }
}

internal fun GameCategoryUiModelMapper.mapToUiModels(
    games: List<Game>,
): List<GameCategoryUiModel> {
    return games.map(::mapToUiModel)
}
