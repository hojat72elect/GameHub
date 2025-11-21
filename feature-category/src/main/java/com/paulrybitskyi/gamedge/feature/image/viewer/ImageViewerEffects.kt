package com.paulrybitskyi.gamedge.feature.image.viewer

import com.paulrybitskyi.gamedge.common.ui.base.events.Command
import com.paulrybitskyi.gamedge.common.ui.base.events.Direction

internal sealed class ImageViewerCommand : Command {
    data class ShareText(val text: String) : ImageViewerCommand()
}

sealed class ImageViewerDirection : Direction {
    data object Back : ImageViewerDirection()
}
