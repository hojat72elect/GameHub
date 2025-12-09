package ca.six.hojat.gamehub.common.data.auth.datastores.database

import ca.six.hojat.gamehub.common.domain.auth.datastores.AuthLocalDataStore
import ca.six.hojat.gamehub.common.domain.auth.entities.OauthCredentials
import ca.six.hojat.gamehub.shared.data.local.auth.DbAuthMapper
import ca.six.hojat.gamehub.shared.data.local.auth.tables.AuthTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthDatabaseDataStore @Inject constructor(
    private val authTable: AuthTable,
    private val dbAuthMapper: DbAuthMapper,
) : AuthLocalDataStore {

    override suspend fun saveOauthCredentials(oauthCredentials: OauthCredentials) {
        authTable.save(dbAuthMapper.mapToDbOauthCredentials(oauthCredentials))
    }

    override suspend fun getOauthCredentials(): OauthCredentials? {
        return authTable.get()?.let(dbAuthMapper::mapToDomainOauthCredentials)
    }
}
