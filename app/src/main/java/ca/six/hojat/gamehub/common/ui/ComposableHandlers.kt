package ca.six.hojat.gamehub.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import ca.six.hojat.gamehub.common.extensions.showLongToast
import ca.six.hojat.gamehub.common.extensions.showShortToast
import ca.six.hojat.gamehub.common.ui.base.BaseViewModel
import ca.six.hojat.gamehub.common.ui.base.events.Command
import ca.six.hojat.gamehub.common.ui.base.events.Direction
import ca.six.hojat.gamehub.common.ui.base.events.common.GeneralCommand

@Composable
fun CommandsHandler(
    viewModel: BaseViewModel,
    onCommand: ((Command) -> Unit)? = null,
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.commandFlow.collect { command ->
            when (command) {
                is GeneralCommand.ShowShortToast -> context.showShortToast(command.message)
                is GeneralCommand.ShowLongToast -> context.showLongToast(command.message)
                else -> onCommand?.invoke(command)
            }
        }
    }
}

@Composable
fun DirectionsHandler(
    viewModel: BaseViewModel,
    onNavigate: (Direction) -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.directionFlow
            .collect(onNavigate)
    }
}
