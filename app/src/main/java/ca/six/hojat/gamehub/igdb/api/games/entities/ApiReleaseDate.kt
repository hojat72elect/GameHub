package ca.six.hojat.gamehub.igdb.api.games.entities

import ca.six.hojat.gamehub.igdb.apicalypse.serialization.annotations.Apicalypse
import ca.six.hojat.gamehub.igdb.apicalypse.serialization.annotations.ApicalypseClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@ApicalypseClass
@Serializable
data class ApiReleaseDate(
    @Apicalypse(Schema.DATE)
    @SerialName(Schema.DATE)
    val date: Long? = null,
    @Apicalypse(Schema.YEAR)
    @SerialName(Schema.YEAR)
    val year: Int? = null,
    @Apicalypse(Schema.CATEGORY)
    @SerialName(Schema.CATEGORY)
    val category: ApiReleaseDateCategory,
) {

    object Schema {
        const val DATE = "date"
        const val YEAR = "y"
        const val CATEGORY = "date_format"
    }
}
