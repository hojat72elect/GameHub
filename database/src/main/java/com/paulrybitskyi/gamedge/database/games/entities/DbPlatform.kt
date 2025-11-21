
package com.paulrybitskyi.gamedge.database.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class DbPlatform(
    val abbreviation: String?,
    val name: String,
)
