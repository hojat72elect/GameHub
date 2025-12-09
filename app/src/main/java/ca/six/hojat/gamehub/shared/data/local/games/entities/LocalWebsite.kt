package ca.six.hojat.gamehub.shared.data.local.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class LocalWebsite(
    val id: Int,
    val url: String,
    val category: LocalWebsiteCategory,
)
