package ca.six.hojat.gamehub.igdb.api.games.entities

import ca.six.hojat.gamehub.igdb.apicalypse.serialization.annotations.Apicalypse
import ca.six.hojat.gamehub.igdb.apicalypse.serialization.annotations.ApicalypseClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@ApicalypseClass
@Serializable
data class ApiWebsite(
    @Apicalypse(Schema.ID)
    @SerialName(Schema.ID)
    val id: Int,
    @Apicalypse(Schema.URL)
    @SerialName(Schema.URL)
    val url: String,
    @Apicalypse(Schema.CATEGORY)
    @SerialName(Schema.CATEGORY)
    val category: ApiWebsiteCategory,
) {

    object Schema {
        const val ID = "id"
        const val URL = "url"
        const val CATEGORY = "type"
    }
}
