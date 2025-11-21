package com.paulrybitskyi.gamedge.common.domain.auth.datastores

import com.paulrybitskyi.gamedge.common.domain.auth.entities.OauthCredentials
import com.paulrybitskyi.gamedge.common.domain.common.DomainResult

interface AuthRemoteDataStore {
    suspend fun getOauthCredentials(): DomainResult<OauthCredentials>
}
