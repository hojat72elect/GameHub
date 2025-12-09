package ca.six.hojat.gamehub.shared.data.local.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class LocalImage(
    val id: String,
    val width: Int?,
    val height: Int?,
)
