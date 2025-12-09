package ca.six.hojat.gamehub.shared.data.local.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class DbPlatform(
    val abbreviation: String?,
    val name: String,
)
