package ca.six.hojat.gamehub.shared.data.local.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class LocalPlatform(
    val abbreviation: String?,
    val name: String,
)
