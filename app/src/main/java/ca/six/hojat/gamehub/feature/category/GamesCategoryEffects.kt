@file:Suppress("MatchingDeclarationName")

package ca.six.hojat.gamehub.feature.category

import ca.six.hojat.gamehub.common.ui.base.events.Direction

sealed class GamesCategoryDirection : Direction {
    data class Info(val gameId: Int) : GamesCategoryDirection()
    data object Back : GamesCategoryDirection()
}
