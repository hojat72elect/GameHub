package ca.six.hojat.gamehub.common.domain.games.common

import ca.six.hojat.gamehub.common.domain.common.entities.Pagination

data class RefreshGamesUseCaseParams(
    val pagination: Pagination = Pagination(),
)
