package ca.six.hojat.gamehub.shared.data.local.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class DbWebsite(
    val id: Int,
    val url: String,
    val category: DbWebsiteCategory,
)
