package ca.six.hojat.gamehub.igdb.api.auth

import ca.six.hojat.gamehub.common.api.asConverterFactory
import ca.six.hojat.gamehub.common.api.calladapter.ApiResultCallAdapterFactory
import ca.six.hojat.gamehub.igdb.api.common.TwitchConstantsProvider
import ca.six.hojat.gamehub.igdb.api.common.di.qualifiers.Endpoint
import ca.six.hojat.gamehub.igdb.api.common.di.qualifiers.IgdbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AuthEndpointModule {

    @Provides
    @Singleton
    fun provideAuthEndpoint(
        authService: AuthService,
        twitchConstantsProvider: TwitchConstantsProvider,
    ): AuthEndpoint {
        return AuthEndpointImpl(
            authService = authService,
            clientId = twitchConstantsProvider.clientId,
            clientSecret = twitchConstantsProvider.clientSecret,
        )
    }

    @Provides
    fun provideAuthService(@Endpoint(Endpoint.Type.AUTH) retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Endpoint(Endpoint.Type.AUTH)
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @IgdbApi callAdapterFactory: ApiResultCallAdapterFactory,
        json: Json,
        twitchConstantsProvider: TwitchConstantsProvider,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(json.asConverterFactory())
            .baseUrl(twitchConstantsProvider.apiBaseUrl)
            .build()
    }
}
