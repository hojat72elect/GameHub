
package com.paulrybitskyi.gamedge.core

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.common.testing.domain.DOMAIN_GAME
import org.junit.Before
import org.junit.Test

internal class GameLikeCountCalculatorImplTest {

    private lateinit var SUT: GameLikeCountCalculatorImpl

    @Before
    fun setup() {
        SUT = GameLikeCountCalculatorImpl()
    }

    @Test
    fun `Calculates like count properly when hype count field is not null`() {
        val game = DOMAIN_GAME.copy(hypeCount = 10)

        assertThat(SUT.calculateLikeCount(game)).isEqualTo(10)
    }

    @Test
    fun `Calculates like count properly when hype count field is null`() {
        val game = DOMAIN_GAME.copy(hypeCount = null)

        assertThat(SUT.calculateLikeCount(game)).isEqualTo(0)
    }
}
