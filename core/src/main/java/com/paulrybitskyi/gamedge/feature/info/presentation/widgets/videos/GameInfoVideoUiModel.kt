package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.videos

import androidx.compose.runtime.Immutable

@Immutable
internal data class GameInfoVideoUiModel(
    val id: String,
    val thumbnailUrl: String,
    val videoUrl: String,
    val title: String,
)
