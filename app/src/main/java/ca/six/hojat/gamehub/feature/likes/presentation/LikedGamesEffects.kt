@file:Suppress("MatchingDeclarationName")

package ca.six.hojat.gamehub.feature.likes.presentation

import ca.six.hojat.gamehub.common.ui.base.events.Direction

sealed class LikedGamesDirection : Direction {
    data object Search : LikedGamesDirection()
    data class Info(val gameId: Int) : LikedGamesDirection()
}
