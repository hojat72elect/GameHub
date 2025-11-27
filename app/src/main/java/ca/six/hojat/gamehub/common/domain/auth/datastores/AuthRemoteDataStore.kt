package ca.six.hojat.gamehub.common.domain.auth.datastores

import ca.six.hojat.gamehub.common.domain.auth.entities.OauthCredentials
import ca.six.hojat.gamehub.common.domain.common.DomainResult

interface AuthRemoteDataStore {
    suspend fun getOauthCredentials(): DomainResult<OauthCredentials>
}
