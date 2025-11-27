package ca.six.hojat.gamehub.igdb.api.games.entities

import ca.six.hojat.gamehub.igdb.apicalypse.serialization.annotations.Apicalypse
import ca.six.hojat.gamehub.igdb.apicalypse.serialization.annotations.ApicalypseClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@ApicalypseClass
@Serializable
data class ApiGenre(
    @Apicalypse(Schema.NAME)
    @SerialName(Schema.NAME)
    val name: String,
) {

    object Schema {
        const val NAME = "name"
    }
}
