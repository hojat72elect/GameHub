
package com.paulrybitskyi.gamedge.igdb.api.common

import com.paulrybitskyi.gamedge.common.api.HttpHeaders
import com.paulrybitskyi.gamedge.igdb.api.auth.Authorizer
import com.paulrybitskyi.gamedge.igdb.api.auth.entities.ApiAuthorizationType
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthorizationInterceptor(
    private val credentialsStore: CredentialsStore,
    private val authorizer: Authorizer,
    private val clientId: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val authorizationHeader = authorizer.buildAuthorizationHeader(
            type = ApiAuthorizationType.BEARER,
            token = getAccessToken(),
        )
        val authorizedRequest = chain.request()
            .newBuilder()
            .addHeader(ApiHeaders.CLIENT_ID, clientId)
            .addHeader(HttpHeaders.AUTHORIZATION, authorizationHeader)
            .build()

        chain.proceed(authorizedRequest)
    }

    private suspend fun getAccessToken(): String {
        return credentialsStore.getLocalOauthCredentials()
            ?.accessToken
            .orEmpty()
    }
}
