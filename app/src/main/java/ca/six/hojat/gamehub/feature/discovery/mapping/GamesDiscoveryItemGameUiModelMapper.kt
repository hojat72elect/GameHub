package ca.six.hojat.gamehub.feature.discovery.mapping

import ca.six.hojat.gamehub.common.domain.games.entities.Game
import ca.six.hojat.gamehub.core.factories.IgdbImageSize
import ca.six.hojat.gamehub.core.factories.IgdbImageUrlFactory
import ca.six.hojat.gamehub.feature.discovery.widgets.GamesDiscoveryItemGameUiModel
import javax.inject.Inject

internal interface GamesDiscoveryItemGameUiModelMapper {
    fun mapToUiModel(game: Game): GamesDiscoveryItemGameUiModel
}

internal class GamesDiscoveryItemGameUiModelMapperImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
) : GamesDiscoveryItemGameUiModelMapper {

    override fun mapToUiModel(game: Game): GamesDiscoveryItemGameUiModel {
        return GamesDiscoveryItemGameUiModel(
            id = game.id,
            title = game.name,
            coverUrl = game.cover?.let { cover ->
                igdbImageUrlFactory.createUrl(cover, IgdbImageUrlFactory.Config(IgdbImageSize.BIG_COVER))
            },
        )
    }
}

internal fun GamesDiscoveryItemGameUiModelMapper.mapToUiModels(
    games: List<Game>,
): List<GamesDiscoveryItemGameUiModel> {
    return games.map(::mapToUiModel)
}
