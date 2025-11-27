package ca.six.hojat.gamehub.common.ui.base.events.common

import ca.six.hojat.gamehub.common.ui.base.events.Command

sealed class GeneralCommand : Command {
    class ShowShortToast(val message: String) : GeneralCommand()
    class ShowLongToast(val message: String) : GeneralCommand()
}
