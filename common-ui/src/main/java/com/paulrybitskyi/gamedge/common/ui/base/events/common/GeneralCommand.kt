
package com.paulrybitskyi.gamedge.common.ui.base.events.common

import com.paulrybitskyi.gamedge.common.ui.base.events.Command

sealed class GeneralCommand : Command {
    class ShowShortToast(val message: String) : GeneralCommand()
    class ShowLongToast(val message: String) : GeneralCommand()
}
