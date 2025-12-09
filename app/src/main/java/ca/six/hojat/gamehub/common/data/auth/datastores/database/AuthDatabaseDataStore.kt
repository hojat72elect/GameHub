package ca.six.hojat.gamehub.common.data.auth.datastores.database

import ca.six.hojat.gamehub.common.domain.auth.datastores.AuthLocalDataStore
import ca.six.hojat.gamehub.common.domain.auth.entities.OauthCredentials
import ca.six.hojat.gamehub.shared.data.local.authentication.LocalAuthenticationMapper
import ca.six.hojat.gamehub.shared.data.local.authentication.tables.AuthenticationTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthDatabaseDataStore @Inject constructor(
    private val authenticationTable: AuthenticationTable,
    private val localAuthenticationMapper: LocalAuthenticationMapper,
) : AuthLocalDataStore {

    override suspend fun saveOauthCredentials(oauthCredentials: OauthCredentials) {
        authenticationTable.save(localAuthenticationMapper.mapToDbOauthCredentials(oauthCredentials))
    }

    override suspend fun getOauthCredentials(): OauthCredentials? {
        return authenticationTable.get()?.let(localAuthenticationMapper::mapToDomainOauthCredentials)
    }
}
