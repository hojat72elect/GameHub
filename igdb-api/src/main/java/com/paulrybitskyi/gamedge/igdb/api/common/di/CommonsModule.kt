package com.paulrybitskyi.gamedge.igdb.api.common.di

import com.paulrybitskyi.gamedge.common.api.ErrorMessageExtractor
import com.paulrybitskyi.gamedge.common.api.addInterceptorAsFirstInChain
import com.paulrybitskyi.gamedge.common.api.calladapter.ApiResultCallAdapterFactory
import com.paulrybitskyi.gamedge.igdb.api.auth.Authorizer
import com.paulrybitskyi.gamedge.igdb.api.common.AuthorizationInterceptor
import com.paulrybitskyi.gamedge.igdb.api.common.CredentialsStore
import com.paulrybitskyi.gamedge.igdb.api.common.TwitchConstantsProvider
import com.paulrybitskyi.gamedge.igdb.api.common.di.qualifiers.IgdbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CommonsModule {

    @Provides
    @Singleton
    @IgdbApi
    fun provideOkHttpClient(
        okHttpClient: OkHttpClient,
        authorizationInterceptor: AuthorizationInterceptor,
        authenticator: Authenticator,
    ): OkHttpClient {
        return okHttpClient.newBuilder()
            .addInterceptorAsFirstInChain(authorizationInterceptor)
            .authenticator(authenticator)
            .build()
    }

    @Provides
    @IgdbApi
    fun provideApiResultCallAdapterFactory(
        @IgdbApi errorMessageExtractor: ErrorMessageExtractor,
    ): ApiResultCallAdapterFactory {
        return ApiResultCallAdapterFactory(errorMessageExtractor)
    }

    @Provides
    fun provideAuthorizationInterceptor(
        credentialsStore: CredentialsStore,
        authorizer: Authorizer,
        twitchConstantsProvider: TwitchConstantsProvider,
    ): AuthorizationInterceptor {
        return AuthorizationInterceptor(
            credentialsStore = credentialsStore,
            authorizer = authorizer,
            clientId = twitchConstantsProvider.clientId,
        )
    }
}
