package com.paulrybitskyi.gamedge.feature.search.presentation

import com.paulrybitskyi.gamedge.common.ui.base.events.Command
import com.paulrybitskyi.gamedge.common.ui.base.events.Direction

internal sealed class GamesSearchCommand : Command {
    data object ClearItems : GamesSearchCommand()
}

sealed class GamesSearchDirection : Direction {
    data class Info(val gameId: Int) : GamesSearchDirection()
    data object Back : GamesSearchDirection()
}
