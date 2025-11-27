package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.links

import androidx.compose.runtime.Immutable

@Immutable
internal data class GameInfoLinkUiModel(
    val id: Int,
    val text: String,
    val iconId: Int,
    val url: String,
)
