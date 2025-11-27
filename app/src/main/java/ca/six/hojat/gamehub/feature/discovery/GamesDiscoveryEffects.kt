@file:Suppress("MatchingDeclarationName")

package ca.six.hojat.gamehub.feature.discovery

import ca.six.hojat.gamehub.common.ui.base.events.Direction

sealed class GamesDiscoveryDirection : Direction {
    data object Search : GamesDiscoveryDirection()
    data class Category(val category: String) : GamesDiscoveryDirection()
    data class Info(val gameId: Int) : GamesDiscoveryDirection()
}
