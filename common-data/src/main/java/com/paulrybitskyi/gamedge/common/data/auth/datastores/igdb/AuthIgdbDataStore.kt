package com.paulrybitskyi.gamedge.common.data.auth.datastores.igdb

import com.github.michaelbull.result.mapEither
import com.paulrybitskyi.gamedge.common.data.common.ApiErrorMapper
import com.paulrybitskyi.gamedge.common.domain.auth.datastores.AuthRemoteDataStore
import com.paulrybitskyi.gamedge.common.domain.auth.entities.OauthCredentials
import com.paulrybitskyi.gamedge.common.domain.common.DomainResult
import com.paulrybitskyi.gamedge.igdb.api.auth.AuthEndpoint
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
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
