
package com.paulrybitskyi.gamedge.feature.info.domain.entities

import com.paulrybitskyi.gamedge.common.domain.games.entities.Game

data class GameInfo(
    val game: Game,
    val isGameLiked: Boolean,
    val companyGames: List<Game>,
    val similarGames: List<Game>,
)
