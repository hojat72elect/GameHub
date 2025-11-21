
package com.paulrybitskyi.gamedge.common.domain.auth.entities

data class OauthCredentials(
    val accessToken: String,
    val tokenType: String,
    val tokenTtl: Long,
)
