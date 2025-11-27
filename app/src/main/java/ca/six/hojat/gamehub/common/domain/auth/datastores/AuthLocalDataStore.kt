package ca.six.hojat.gamehub.common.domain.auth.datastores

import ca.six.hojat.gamehub.common.domain.auth.entities.OauthCredentials

interface AuthLocalDataStore {
    suspend fun saveOauthCredentials(oauthCredentials: OauthCredentials)
    suspend fun getOauthCredentials(): OauthCredentials?
}
