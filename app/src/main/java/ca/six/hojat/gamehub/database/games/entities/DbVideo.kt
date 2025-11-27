package ca.six.hojat.gamehub.database.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class DbVideo(
    val id: String,
    val name: String?,
)
