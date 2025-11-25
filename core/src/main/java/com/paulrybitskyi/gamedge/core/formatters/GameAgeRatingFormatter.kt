package com.paulrybitskyi.gamedge.core.formatters

import com.paulrybitskyi.gamedge.common.domain.games.entities.AgeRatingCategory
import com.paulrybitskyi.gamedge.common.domain.games.entities.AgeRatingType
import com.paulrybitskyi.gamedge.common.domain.games.entities.Game
import com.paulrybitskyi.gamedge.core.R
import com.paulrybitskyi.gamedge.core.providers.StringProvider
import javax.inject.Inject

interface GameAgeRatingFormatter {
    fun formatAgeRating(game: Game): String
}

internal class GameAgeRatingFormatterImpl @Inject constructor(
    private val stringProvider: StringProvider,
) : GameAgeRatingFormatter {

    override fun formatAgeRating(game: Game): String {
        val ageRatings = game.ageRatings.filterNot {
            it.category == AgeRatingCategory.UNKNOWN ||
                    it.type == AgeRatingType.UNKNOWN
        }

        val ageRating = ageRatings.firstOrNull { it.category == AgeRatingCategory.PEGI }
            ?: ageRatings.firstOrNull { it.category == AgeRatingCategory.ESRB }
            ?: return stringProvider.getString(R.string.not_available_abbr)

        return stringProvider.getString(
            R.string.age_rating_template,
            ageRating.category.title,
            ageRating.type.title,
        )
    }
}
