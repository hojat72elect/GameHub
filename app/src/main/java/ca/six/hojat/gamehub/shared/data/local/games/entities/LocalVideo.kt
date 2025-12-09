package ca.six.hojat.gamehub.shared.data.local.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class LocalVideo(
    val id: String,
    val name: String?,
)
