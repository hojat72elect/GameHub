package com.paulrybitskyi.gamedge.common.testing.domain

import com.paulrybitskyi.gamedge.common.domain.common.entities.Error
import com.paulrybitskyi.gamedge.common.domain.common.entities.Pagination
import com.paulrybitskyi.gamedge.common.domain.games.entities.Category
import com.paulrybitskyi.gamedge.common.domain.games.entities.Game

val DOMAIN_GAME = Game(
    id = 1,
    hypeCount = null,
    releaseDate = null,
    criticsRating = null,
    usersRating = null,
    totalRating = null,
    name = "name",
    summary = null,
    storyline = null,
    category = Category.UNKNOWN,
    cover = null,
    releaseDates = listOf(),
    ageRatings = listOf(),
    videos = listOf(),
    artworks = listOf(),
    screenshots = listOf(),
    genres = listOf(),
    platforms = listOf(),
    playerPerspectives = listOf(),
    themes = listOf(),
    modes = listOf(),
    keywords = listOf(),
    involvedCompanies = listOf(),
    websites = listOf(),
    similarGames = listOf(),
)
val DOMAIN_GAMES = listOf(
    DOMAIN_GAME.copy(id = 1),
    DOMAIN_GAME.copy(id = 2),
    DOMAIN_GAME.copy(id = 3),
)

val DOMAIN_ERROR_UNKNOWN = Error.Unknown("message")

val PAGINATION = Pagination(offset = 0, limit = 20)
