package ca.six.hojat.gamehub.shared.data.local.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class LocalCompany(
    val id: Int,
    val name: String,
    val websiteUrl: String,
    val logo: LocalImage?,
    val developedGames: List<Int>,
)
