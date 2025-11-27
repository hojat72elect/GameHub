package ca.six.hojat.gamehub.igdb.api.auth

import ca.six.hojat.gamehub.common.api.ApiResult
import ca.six.hojat.gamehub.igdb.api.auth.entities.ApiGrantType
import ca.six.hojat.gamehub.igdb.api.auth.entities.ApiOauthCredentials

interface AuthEndpoint {
    suspend fun getOauthCredentials(): ApiResult<ApiOauthCredentials>
}

internal class AuthEndpointImpl(
    private val authService: AuthService,
    private val clientId: String,
    private val clientSecret: String,
) : AuthEndpoint {

    override suspend fun getOauthCredentials(): ApiResult<ApiOauthCredentials> {
        return authService.getOauthCredentials(
            clientId = clientId,
            clientSecret = clientSecret,
            grantType = ApiGrantType.CLIENT_CREDENTIALS.rawType,
        )
    }
}
