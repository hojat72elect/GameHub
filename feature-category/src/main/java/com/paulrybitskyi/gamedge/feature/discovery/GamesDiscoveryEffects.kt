

@file:Suppress("MatchingDeclarationName")

package com.paulrybitskyi.gamedge.feature.discovery

import com.paulrybitskyi.gamedge.common.ui.base.events.Direction

sealed class GamesDiscoveryDirection : Direction {
    data object Search : GamesDiscoveryDirection()
    data class Category(val category: String) : GamesDiscoveryDirection()
    data class Info(val gameId: Int) : GamesDiscoveryDirection()
}
