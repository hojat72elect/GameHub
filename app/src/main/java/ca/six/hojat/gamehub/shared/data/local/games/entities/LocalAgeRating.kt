package ca.six.hojat.gamehub.shared.data.local.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class LocalAgeRating(
    val category: LocalAgeRatingCategory,
    val type: LocalAgeRatingType,
)
