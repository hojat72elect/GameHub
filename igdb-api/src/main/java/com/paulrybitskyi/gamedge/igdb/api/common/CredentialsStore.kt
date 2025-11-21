
package com.paulrybitskyi.gamedge.igdb.api.common

import com.paulrybitskyi.gamedge.igdb.api.auth.entities.ApiOauthCredentials

interface CredentialsStore {
    suspend fun saveOauthCredentials(oauthCredentials: ApiOauthCredentials)
    suspend fun getLocalOauthCredentials(): ApiOauthCredentials?
    suspend fun getRemoteOauthCredentials(): ApiOauthCredentials?
}
