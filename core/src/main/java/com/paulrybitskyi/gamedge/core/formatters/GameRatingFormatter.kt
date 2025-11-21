package com.paulrybitskyi.gamedge.core.formatters

import com.paulrybitskyi.gamedge.core.R
import com.paulrybitskyi.gamedge.core.providers.StringProvider
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject
import kotlin.math.roundToInt

interface GameRatingFormatter {
    fun formatRating(rating: Double?): String
}

@BindType
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
