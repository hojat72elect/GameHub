package com.paulrybitskyi.gamedge.igdb.api.games.entities

import com.paulrybitskyi.gamedge.igdb.apicalypse.serialization.annotations.Apicalypse
import com.paulrybitskyi.gamedge.igdb.apicalypse.serialization.annotations.ApicalypseClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@ApicalypseClass
@Serializable
data class ApiAgeRating(
    @Apicalypse(Schema.CATEGORY)
    @SerialName(Schema.CATEGORY)
    val category: ApiAgeRatingCategory,
    @Apicalypse(Schema.RATING)
    @SerialName(Schema.RATING)
    val type: ApiAgeRatingType,
) {

    object Schema {
        const val CATEGORY = "organization"
        const val RATING = "rating_category"
    }
}
