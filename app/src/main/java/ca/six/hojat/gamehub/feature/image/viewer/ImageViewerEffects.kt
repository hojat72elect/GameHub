package ca.six.hojat.gamehub.feature.image.viewer

import ca.six.hojat.gamehub.common.ui.base.events.Command
import ca.six.hojat.gamehub.common.ui.base.events.Direction

internal sealed class ImageViewerCommand : Command {
    data class ShareText(val text: String) : ImageViewerCommand()
}

sealed class ImageViewerDirection : Direction {
    data object Back : ImageViewerDirection()
}
