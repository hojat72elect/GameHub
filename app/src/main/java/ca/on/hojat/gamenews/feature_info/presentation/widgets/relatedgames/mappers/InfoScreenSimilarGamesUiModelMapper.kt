package ca.on.hojat.gamenews.feature_info.presentation.widgets.relatedgames.mappers

import ca.on.hojat.gamenews.R
import ca.on.hojat.gamenews.core.providers.StringProvider
import ca.on.hojat.gamenews.feature_info.presentation.widgets.relatedgames.RelatedGameUiModel
import ca.on.hojat.gamenews.feature_info.presentation.widgets.relatedgames.RelatedGamesType
import ca.on.hojat.gamenews.feature_info.presentation.widgets.relatedgames.RelatedGamesUiModel
import ca.on.hojat.gamenews.core.factories.IgdbImageSize
import ca.on.hojat.gamenews.core.factories.IgdbImageUrlFactory
import ca.on.hojat.gamenews.core.domain.entities.Game
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

internal interface InfoScreenSimilarGamesUiModelMapper {
    fun mapToUiModel(similarGames: List<Game>): RelatedGamesUiModel?
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class InfoScreenSimilarGamesUiModelMapperImpl @Inject constructor(
    private val stringProvider: StringProvider,
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
) : InfoScreenSimilarGamesUiModelMapper {

    override fun mapToUiModel(similarGames: List<Game>): RelatedGamesUiModel? {
        if (similarGames.isEmpty()) return null

        return RelatedGamesUiModel(
            type = RelatedGamesType.SIMILAR_GAMES,
            title = stringProvider.getString(R.string.game_info_similar_games_title),
            items = similarGames.toRelatedGameUiModels(),
        )
    }

    private fun List<Game>.toRelatedGameUiModels(): List<RelatedGameUiModel> {
        return map {
            RelatedGameUiModel(
                id = it.id,
                title = it.name,
                coverUrl = it.cover?.let { cover ->
                    igdbImageUrlFactory.createUrl(
                        cover,
                        IgdbImageUrlFactory.Config(IgdbImageSize.BIG_COVER)
                    )
                }
            )
        }
    }
}