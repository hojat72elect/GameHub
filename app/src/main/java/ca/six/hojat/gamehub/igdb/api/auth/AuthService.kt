package ca.six.hojat.gamehub.igdb.api.auth

import ca.six.hojat.gamehub.common.api.ApiResult
import ca.six.hojat.gamehub.igdb.api.auth.entities.ApiOauthCredentials
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
