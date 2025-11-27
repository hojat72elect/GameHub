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

internal interface GameInfoOtherCompanyGamesUiModelMapper {

    fun mapToUiModel(
        companyGames: List<Game>,
        currentGame: Game,
    ): GameInfoRelatedGamesUiModel?
}

internal class GameInfoOtherCompanyGamesUiModelMapperImpl @Inject constructor(
    private val stringProvider: StringProvider,
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
) : GameInfoOtherCompanyGamesUiModelMapper {

    override fun mapToUiModel(
        companyGames: List<Game>,
        currentGame: Game,
    ): GameInfoRelatedGamesUiModel? {
        return companyGames
            .filter { it.id != currentGame.id }
            .takeIf(List<Game>::isNotEmpty)
            ?.let { games ->
                GameInfoRelatedGamesUiModel(
                    type = GameInfoRelatedGamesType.OTHER_COMPANY_GAMES,
                    title = currentGame.createOtherCompanyGamesModelTitle(),
                    items = games.toRelatedGameUiModels(),
                )
            }
    }

    private fun Game.createOtherCompanyGamesModelTitle(): String {
        val developerName = developerCompany?.name
            ?: stringProvider.getString(R.string.game_info_other_company_games_title_default_arg)

        val title = stringProvider.getString(
            R.string.game_info_other_company_games_title_template,
            developerName,
        )

        return title
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
