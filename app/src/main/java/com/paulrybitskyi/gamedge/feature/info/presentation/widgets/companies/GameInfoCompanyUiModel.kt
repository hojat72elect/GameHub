package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.companies

import androidx.compose.runtime.Immutable

@Immutable
internal data class GameInfoCompanyUiModel(
    val id: Int,
    val logoUrl: String?,
    val logoWidth: Int?,
    val logoHeight: Int?,
    val websiteUrl: String,
    val name: String,
    val roles: String,
) {

    val hasLogoSize: Boolean
        get() = ((logoWidth != null) && (logoHeight != null))
}
