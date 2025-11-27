package ca.six.hojat.gamehub.database.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class DbCompany(
    val id: Int,
    val name: String,
    val websiteUrl: String,
    val logo: DbImage?,
    val developedGames: List<Int>,
)
