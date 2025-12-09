package ca.six.hojat.gamehub.shared.data.local.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class DbVideo(
    val id: String,
    val name: String?,
)
