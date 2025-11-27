package ca.six.hojat.gamehub.feature.info.domain.entities

import ca.six.hojat.gamehub.common.domain.games.entities.Game

data class GameInfo(
    val game: Game,
    val isGameLiked: Boolean,
    val companyGames: List<Game>,
    val similarGames: List<Game>,
)
