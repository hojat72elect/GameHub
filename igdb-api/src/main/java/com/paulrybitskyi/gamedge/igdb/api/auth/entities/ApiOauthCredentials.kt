package com.paulrybitskyi.gamedge.igdb.api.auth.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiOauthCredentials(
    @SerialName(Schema.ACCESS_TOKEN)
    val accessToken: String,
    @SerialName(Schema.TOKEN_TYPE)
    val tokenType: String,
    @SerialName(Schema.TOKEN_TTL)
    val tokenTtl: Long,
) {

    object Schema {
        const val ACCESS_TOKEN = "access_token"
        const val TOKEN_TYPE = "token_type"
        const val TOKEN_TTL = "expires_in"
    }
}
