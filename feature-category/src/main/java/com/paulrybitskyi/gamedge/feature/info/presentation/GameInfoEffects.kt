
package com.paulrybitskyi.gamedge.feature.info.presentation

import com.paulrybitskyi.gamedge.common.ui.base.events.Command
import com.paulrybitskyi.gamedge.common.ui.base.events.Direction

internal sealed class GameInfoCommand : Command {
    data class OpenUrl(val url: String) : GameInfoCommand()
}

sealed class GameInfoDirection : Direction {
    data class Info(val gameId: Int) : GameInfoDirection()

    data class ImageViewer(
        val imageUrls: List<String>,
        val title: String?,
        val initialPosition: Int,
    ) : GameInfoDirection()

    data object Back : GameInfoDirection()
}
