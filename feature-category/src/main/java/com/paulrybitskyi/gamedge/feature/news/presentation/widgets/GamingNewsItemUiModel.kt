
package com.paulrybitskyi.gamedge.feature.news.presentation.widgets

import androidx.compose.runtime.Immutable

@Immutable
internal data class GamingNewsItemUiModel(
    val id: Int,
    val imageUrl: String?,
    val title: String,
    val lede: String,
    val publicationDate: String,
    val siteDetailUrl: String,
) {

    val hasImageUrl: Boolean
        get() = imageUrl != null
}
