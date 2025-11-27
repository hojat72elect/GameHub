package ca.six.hojat.gamehub.common.domain.auth.entities

data class OauthCredentials(
    val accessToken: String,
    val tokenType: String,
    val tokenTtl: Long,
)
