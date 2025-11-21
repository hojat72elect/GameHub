package com.paulrybitskyi.gamedge.core

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.common.domain.games.entities.AgeRating
import com.paulrybitskyi.gamedge.common.domain.games.entities.AgeRatingCategory
import com.paulrybitskyi.gamedge.common.domain.games.entities.AgeRatingType
import com.paulrybitskyi.gamedge.common.testing.domain.DOMAIN_GAME
import com.paulrybitskyi.gamedge.core.formatters.GameAgeRatingFormatterImpl
import com.paulrybitskyi.gamedge.core.providers.StringProvider
import org.junit.Before
import org.junit.Test

internal class GameAgeRatingFormatterImplTest {

    private lateinit var stringProvider: FakeStringProvider
    private lateinit var SUT: GameAgeRatingFormatterImpl

    @Before
    fun setup() {
        stringProvider = FakeStringProvider()
        SUT = GameAgeRatingFormatterImpl(stringProvider)
    }

    @Test
    fun `Returns properly formatted string with age rating`() {
        val game = DOMAIN_GAME.copy(
            ageRatings = listOf(
                AgeRating(AgeRatingCategory.PEGI, AgeRatingType.AO),
            ),
        )

        SUT.formatAgeRating(game)

        assertThat(stringProvider.isRatingAvailable).isTrue()
    }

    @Test
    fun `Returns not available string when game does not contain any ratings`() {
        SUT.formatAgeRating(DOMAIN_GAME)

        assertThat(stringProvider.isRatingNotAvailable).isTrue()
    }

    @Test
    fun `Returns not available string when game does not contain valid ratings`() {
        val game = DOMAIN_GAME.copy(
            ageRatings = listOf(
                AgeRating(AgeRatingCategory.UNKNOWN, AgeRatingType.AO),
                AgeRating(AgeRatingCategory.PEGI, AgeRatingType.UNKNOWN),
            ),
        )

        SUT.formatAgeRating(game)

        assertThat(stringProvider.isRatingNotAvailable).isTrue()
    }

    private class FakeStringProvider : StringProvider {

        var isRatingAvailable = false
        var isRatingNotAvailable = false

        override fun getString(id: Int, vararg args: Any): String {
            isRatingAvailable = (id == R.string.age_rating_template)
            isRatingNotAvailable = (id == R.string.not_available_abbr)

            return ""
        }

        override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any): String {
            return ""
        }
    }
}
