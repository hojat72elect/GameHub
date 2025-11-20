
package com.paulrybitskyi.gamedge.feature.discovery.widgets

import androidx.compose.runtime.Immutable

@Immutable
internal data class GamesDiscoveryItemGameUiModel(
    val id: Int,
    val title: String,
    val coverUrl: String?,
)
