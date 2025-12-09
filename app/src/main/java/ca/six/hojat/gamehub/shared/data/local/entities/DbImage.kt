package ca.six.hojat.gamehub.shared.data.local.entities

import kotlinx.serialization.Serializable

@Serializable
data class DbImage(
    val id: String,
    val width: Int?,
    val height: Int?,
)
