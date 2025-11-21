
@file:Suppress("MatchingDeclarationName")

package com.paulrybitskyi.gamedge.feature.likes.presentation

import com.paulrybitskyi.gamedge.common.ui.base.events.Direction

sealed class LikedGamesDirection : Direction {
    data object Search : LikedGamesDirection()
    data class Info(val gameId: Int) : LikedGamesDirection()
}
