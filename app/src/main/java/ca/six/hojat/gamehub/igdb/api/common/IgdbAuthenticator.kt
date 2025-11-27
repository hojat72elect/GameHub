package ca.six.hojat.gamehub.igdb.api.common

import ca.six.hojat.gamehub.common.api.HttpHeaders
import ca.six.hojat.gamehub.igdb.api.auth.AuthHeaderParser
import ca.six.hojat.gamehub.igdb.api.auth.Authorizer
import ca.six.hojat.gamehub.igdb.api.auth.entities.ApiAuthorizationType
import ca.six.hojat.gamehub.igdb.api.auth.entities.ApiOauthCredentials
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

private const val MAX_AUTH_RESPONSE_COUNT = 3

internal class IgdbAuthenticator @Inject constructor(
    private val credentialsStore: CredentialsStore,
    private val authHeaderParser: AuthHeaderParser,
    private val authorizer: Authorizer,
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.responseCount >= MAX_AUTH_RESPONSE_COUNT) {
            return null
        }

        return runBlocking {
            mutex.withLock {
                val request = response.request
                val lastSavedAccessToken = credentialsStore.getLocalOauthCredentials()?.accessToken
                val requestAccessToken = request.getAccessToken()

                if ((lastSavedAccessToken != null) && (requestAccessToken != lastSavedAccessToken)) {
                    request.buildNewRequest(lastSavedAccessToken)
                } else {
                    refreshCredentials()?.let { credentials ->
                        request.buildNewRequest(credentials.accessToken)
                    }
                }
            }
        }
    }

    private fun Request.getAccessToken(): String? {
        return header(HttpHeaders.AUTHORIZATION)
            ?.let(authHeaderParser::parse)
            ?.token
    }

    private suspend fun refreshCredentials(): ApiOauthCredentials? {
        return credentialsStore.getRemoteOauthCredentials()
            ?.also { credentials -> credentialsStore.saveOauthCredentials(credentials) }
    }

    private fun Request.buildNewRequest(newAccessToken: String): Request {
        return newBuilder()
            .header(
                name = HttpHeaders.AUTHORIZATION,
                value = authorizer.buildAuthorizationHeader(
                    type = ApiAuthorizationType.BEARER,
                    token = newAccessToken,
                ),
            )
            .build()
    }
}
