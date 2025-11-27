@file:Suppress("MatchingDeclarationName")

package com.paulrybitskyi.gamedge.feature.category

import com.paulrybitskyi.gamedge.common.ui.base.events.Direction

sealed class GamesCategoryDirection : Direction {
    data class Info(val gameId: Int) : GamesCategoryDirection()
    data object Back : GamesCategoryDirection()
}
