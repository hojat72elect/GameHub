package ca.six.hojat.gamehub.common.data.auth.datastores.igdb

import ca.six.hojat.gamehub.common.domain.auth.datastores.AuthLocalDataStore
import ca.six.hojat.gamehub.common.domain.auth.datastores.AuthRemoteDataStore
import ca.six.hojat.gamehub.igdb.api.auth.entities.ApiOauthCredentials
import ca.six.hojat.gamehub.igdb.api.common.CredentialsStore
import com.github.michaelbull.result.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CredentialsStoreImpl @Inject constructor(
    private val authLocalDataStore: AuthLocalDataStore,
    private val authRemoteDataStore: AuthRemoteDataStore,
    private val igdbAuthMapper: IgdbAuthMapper,
) : CredentialsStore {

    override suspend fun saveOauthCredentials(oauthCredentials: ApiOauthCredentials) {
        authLocalDataStore.saveOauthCredentials(
            igdbAuthMapper.mapToDomainOauthCredentials(oauthCredentials),
        )
    }

    override suspend fun getLocalOauthCredentials(): ApiOauthCredentials? {
        return authLocalDataStore.getOauthCredentials()
            ?.let(igdbAuthMapper::mapToApiOauthCredentials)
    }

    override suspend fun getRemoteOauthCredentials(): ApiOauthCredentials? {
        return authRemoteDataStore.getOauthCredentials()
            .get()
            ?.let(igdbAuthMapper::mapToApiOauthCredentials)
    }
}
