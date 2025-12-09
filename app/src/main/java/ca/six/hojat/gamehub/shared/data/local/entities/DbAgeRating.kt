package ca.six.hojat.gamehub.shared.data.local.entities

import kotlinx.serialization.Serializable

@Serializable
data class DbAgeRating(
    val category: DbAgeRatingCategory,
    val type: DbAgeRatingType,
)
