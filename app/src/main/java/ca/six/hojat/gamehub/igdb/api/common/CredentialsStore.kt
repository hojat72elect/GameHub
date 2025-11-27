package ca.six.hojat.gamehub.igdb.api.common

import ca.six.hojat.gamehub.igdb.api.auth.entities.ApiOauthCredentials

interface CredentialsStore {
    suspend fun saveOauthCredentials(oauthCredentials: ApiOauthCredentials)
    suspend fun getLocalOauthCredentials(): ApiOauthCredentials?
    suspend fun getRemoteOauthCredentials(): ApiOauthCredentials?
}
