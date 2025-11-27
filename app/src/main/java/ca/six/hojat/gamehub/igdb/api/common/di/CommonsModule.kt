package ca.six.hojat.gamehub.igdb.api.common.di

import ca.six.hojat.gamehub.common.api.ErrorMessageExtractor
import ca.six.hojat.gamehub.common.api.addInterceptorAsFirstInChain
import ca.six.hojat.gamehub.common.api.calladapter.ApiResultCallAdapterFactory
import ca.six.hojat.gamehub.igdb.api.auth.Authorizer
import ca.six.hojat.gamehub.igdb.api.common.AuthorizationInterceptor
import ca.six.hojat.gamehub.igdb.api.common.CredentialsStore
import ca.six.hojat.gamehub.igdb.api.common.TwitchConstantsProvider
import ca.six.hojat.gamehub.igdb.api.common.di.qualifiers.IgdbApi
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
