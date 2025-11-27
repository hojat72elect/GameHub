package ca.six.hojat.gamehub.core.formatters

import ca.six.hojat.gamehub.R
import ca.six.hojat.gamehub.core.providers.StringProvider
import javax.inject.Inject
import kotlin.math.roundToInt

interface GameRatingFormatter {
    fun formatRating(rating: Double?): String
}

internal class GameRatingFormatterImpl @Inject constructor(
    private val stringProvider: StringProvider,
) : GameRatingFormatter {

    private companion object {
        private const val MIN_RATING = 0
        private const val MAX_RATING = 100
    }

    override fun formatRating(rating: Double?): String {
        if (rating == null) {
            return stringProvider.getString(R.string.not_available_abbr)
        }

        return stringProvider.getString(
            R.string.rating_template,
            rating.roundToInt().coerceIn(MIN_RATING, MAX_RATING),
        )
    }
}
