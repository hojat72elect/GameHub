package ca.six.hojat.gamehub.common.ui.base

import androidx.lifecycle.ViewModel
import ca.six.hojat.gamehub.common.ui.base.events.Command
import ca.six.hojat.gamehub.common.ui.base.events.Direction
import ca.six.hojat.gamehub.core.markers.Loggable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel : ViewModel(), Loggable {

    override val logTag: String = javaClass.simpleName

    private val commandChannel = Channel<Command>(Channel.BUFFERED)
    private val directionChannel = Channel<Direction>(Channel.BUFFERED)

    val commandFlow: Flow<Command> = commandChannel.receiveAsFlow()
    val directionFlow: Flow<Direction> = directionChannel.receiveAsFlow()

    protected fun dispatchCommand(command: Command) {
        commandChannel.trySend(command)
    }

    protected fun navigate(direction: Direction) {
        directionChannel.trySend(direction)
    }
}
