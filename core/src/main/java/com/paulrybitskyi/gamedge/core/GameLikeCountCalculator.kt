package com.paulrybitskyi.gamedge.core

import com.paulrybitskyi.gamedge.common.domain.games.entities.Game
import javax.inject.Inject

interface GameLikeCountCalculator {
    fun calculateLikeCount(game: Game): Int
}

internal class GameLikeCountCalculatorImpl @Inject constructor() : GameLikeCountCalculator {

    override fun calculateLikeCount(game: Game): Int {
        return game.hypeCount ?: 0
    }
}
