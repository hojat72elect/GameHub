package com.paulrybitskyi.gamedge.igdb.api.auth

import com.paulrybitskyi.gamedge.common.api.ApiResult
import com.paulrybitskyi.gamedge.igdb.api.auth.entities.ApiOauthCredentials
import retrofit2.http.POST
import retrofit2.http.Query

internal interface AuthService {

    @POST("oauth2/token")
    suspend fun getOauthCredentials(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("grant_type") grantType: String,
    ): ApiResult<ApiOauthCredentials>
}
