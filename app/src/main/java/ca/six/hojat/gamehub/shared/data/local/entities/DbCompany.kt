package ca.six.hojat.gamehub.shared.data.local.entities

import kotlinx.serialization.Serializable

@Serializable
data class DbCompany(
    val id: Int,
    val name: String,
    val websiteUrl: String,
    val logo: DbImage?,
    val developedGames: List<Int>,
)
