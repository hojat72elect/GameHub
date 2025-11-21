
package com.paulrybitskyi.gamedge.igdb.api.auth

import com.paulrybitskyi.gamedge.common.api.ApiResult
import com.paulrybitskyi.gamedge.igdb.api.auth.entities.ApiGrantType
import com.paulrybitskyi.gamedge.igdb.api.auth.entities.ApiOauthCredentials

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
