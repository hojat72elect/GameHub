package com.paulrybitskyi.gamedge.common.domain.games.entities

data class Company(
    val id: Int,
    val name: String,
    val websiteUrl: String,
    val logo: Image?,
    val developedGames: List<Int>,
) {

    val hasLogo: Boolean
        get() = (logo != null)

    val hasDevelopedGames: Boolean
        get() = developedGames.isNotEmpty()
}
