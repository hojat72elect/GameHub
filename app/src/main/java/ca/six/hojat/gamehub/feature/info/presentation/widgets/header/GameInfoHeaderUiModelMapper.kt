package ca.six.hojat.gamehub.feature.info.presentation.widgets.header

import ca.six.hojat.gamehub.common.domain.games.entities.Game
import ca.six.hojat.gamehub.core.GameLikeCountCalculator
import ca.six.hojat.gamehub.core.factories.IgdbImageSize
import ca.six.hojat.gamehub.core.factories.IgdbImageUrlFactory
import ca.six.hojat.gamehub.core.formatters.GameAgeRatingFormatter
import ca.six.hojat.gamehub.core.formatters.GameCategoryFormatter
import ca.six.hojat.gamehub.core.formatters.GameRatingFormatter
import ca.six.hojat.gamehub.core.formatters.GameReleaseDateFormatter
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.artworks.GameInfoArtworkUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.artworks.GameInfoArtworkUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.artworks.mapToUiModels
import javax.inject.Inject

internal interface GameInfoHeaderUiModelMapper {
    fun mapToUiModel(game: Game, isLiked: Boolean): GameInfoHeaderUiModel
}

internal class GameInfoHeaderUiModelMapperImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
    private val artworkModelMapper: GameInfoArtworkUiModelMapper,
    private val releaseDateFormatter: GameReleaseDateFormatter,
    private val ratingFormatter: GameRatingFormatter,
    private val likeCountCalculator: GameLikeCountCalculator,
    private val ageRatingFormatter: GameAgeRatingFormatter,
    private val categoryFormatter: GameCategoryFormatter,
) : GameInfoHeaderUiModelMapper {

    override fun mapToUiModel(game: Game, isLiked: Boolean): GameInfoHeaderUiModel {
        return GameInfoHeaderUiModel(
            artworks = game.createArtworks(),
            isLiked = isLiked,
            coverImageUrl = game.createCoverImageUrl(),
            title = game.name,
            releaseDate = game.formatReleaseDate(),
            developerName = game.developerCompany?.name,
            rating = game.formatRating(),
            likeCount = game.calculateLikeCount(),
            ageRating = game.formatAgeRating(),
            gameCategory = game.formatCategory(),
        )
    }

    private fun Game.createArtworks(): List<GameInfoArtworkUiModel> {
        return artworkModelMapper.mapToUiModels(artworks)
    }

    private fun Game.createCoverImageUrl(): String? {
        return cover?.let { cover ->
            igdbImageUrlFactory.createUrl(cover, IgdbImageUrlFactory.Config(IgdbImageSize.BIG_COVER))
        }
    }

    private fun Game.formatReleaseDate(): String {
        return releaseDateFormatter.formatReleaseDate(this)
    }

    private fun Game.formatRating(): String {
        return ratingFormatter.formatRating(totalRating)
    }

    private fun Game.calculateLikeCount(): String {
        return likeCountCalculator.calculateLikeCount(this).toString()
    }

    private fun Game.formatCategory(): String {
        return categoryFormatter.formatCategory(category)
    }

    private fun Game.formatAgeRating(): String {
        return ageRatingFormatter.formatAgeRating(this)
    }
}
