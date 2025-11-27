package ca.six.hojat.gamehub.feature.search.presentation

import ca.six.hojat.gamehub.common.ui.base.events.Command
import ca.six.hojat.gamehub.common.ui.base.events.Direction

internal sealed class GamesSearchCommand : Command {
    data object ClearItems : GamesSearchCommand()
}

sealed class GamesSearchDirection : Direction {
    data class Info(val gameId: Int) : GamesSearchDirection()
    data object Back : GamesSearchDirection()
}
