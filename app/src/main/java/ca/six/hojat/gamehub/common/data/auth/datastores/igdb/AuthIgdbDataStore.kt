package ca.six.hojat.gamehub.common.data.auth.datastores.igdb

import ca.six.hojat.gamehub.common.data.common.ApiErrorMapper
import ca.six.hojat.gamehub.common.domain.auth.datastores.AuthRemoteDataStore
import ca.six.hojat.gamehub.common.domain.auth.entities.OauthCredentials
import ca.six.hojat.gamehub.common.domain.common.DomainResult
import ca.six.hojat.gamehub.igdb.api.auth.AuthEndpoint
import com.github.michaelbull.result.mapEither
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthIgdbDataStore @Inject constructor(
    private val authEndpoint: AuthEndpoint,
    private val igdbAuthMapper: IgdbAuthMapper,
    private val apiErrorMapper: ApiErrorMapper,
) : AuthRemoteDataStore {

    override suspend fun getOauthCredentials(): DomainResult<OauthCredentials> {
        return authEndpoint
            .getOauthCredentials()
            .mapEither(igdbAuthMapper::mapToDomainOauthCredentials, apiErrorMapper::mapToDomainError)
    }
}
