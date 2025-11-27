package ca.six.hojat.gamehub.database.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class DbAgeRating(
    val category: DbAgeRatingCategory,
    val type: DbAgeRatingType,
)
