package ca.six.hojat.gamehub.shared.data.local.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class LocalReleaseDate(
    val date: Long?,
    val year: Int?,
    val category: LocalReleaseDateCategory,
)
