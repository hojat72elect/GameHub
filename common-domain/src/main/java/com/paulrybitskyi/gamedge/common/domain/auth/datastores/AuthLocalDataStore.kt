
package com.paulrybitskyi.gamedge.common.domain.auth.datastores

import com.paulrybitskyi.gamedge.common.domain.auth.entities.OauthCredentials

interface AuthLocalDataStore {
    suspend fun saveOauthCredentials(oauthCredentials: OauthCredentials)
    suspend fun getOauthCredentials(): OauthCredentials?
}
