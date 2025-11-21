package com.paulrybitskyi.gamedge.igdb.api.utils

import com.paulrybitskyi.gamedge.igdb.api.auth.entities.ApiOauthCredentials
import com.paulrybitskyi.gamedge.igdb.api.common.CredentialsStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FakeCredentialsStore @Inject constructor() : CredentialsStore {

    private var oauthCredentials: ApiOauthCredentials? = null

    override suspend fun saveOauthCredentials(oauthCredentials: ApiOauthCredentials) {
        this.oauthCredentials = oauthCredentials
    }

    override suspend fun getLocalOauthCredentials(): ApiOauthCredentials? {
        return oauthCredentials
    }

    override suspend fun getRemoteOauthCredentials(): ApiOauthCredentials? {
        return oauthCredentials
    }
}
