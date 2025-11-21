
package com.paulrybitskyi.gamedge.common.domain.games.common

import com.paulrybitskyi.gamedge.common.domain.common.entities.Pagination

data class RefreshGamesUseCaseParams(
    val pagination: Pagination = Pagination(),
)
