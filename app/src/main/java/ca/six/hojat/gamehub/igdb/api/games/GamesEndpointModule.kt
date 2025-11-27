package ca.six.hojat.gamehub.igdb.api.games

import ca.six.hojat.gamehub.common.api.asConverterFactory
import ca.six.hojat.gamehub.common.api.calladapter.ApiResultCallAdapterFactory
import ca.six.hojat.gamehub.igdb.api.common.IgdbConstantsProvider
import ca.six.hojat.gamehub.igdb.api.common.di.qualifiers.Endpoint
import ca.six.hojat.gamehub.igdb.api.common.di.qualifiers.IgdbApi
import ca.six.hojat.gamehub.igdb.apicalypse.querybuilder.ApicalypseQueryBuilderFactory
import ca.six.hojat.gamehub.igdb.apicalypse.serialization.ApicalypseSerializer
import ca.six.hojat.gamehub.igdb.apicalypse.serialization.ApicalypseSerializerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object GamesEndpointModule {

    @Provides
    fun provideGamesService(@Endpoint(Endpoint.Type.GAMES) retrofit: Retrofit): GamesService {
        return retrofit.create(GamesService::class.java)
    }

    @Provides
    @Endpoint(Endpoint.Type.GAMES)
    fun provideRetrofit(
        @IgdbApi okHttpClient: OkHttpClient,
        @IgdbApi callAdapterFactory: ApiResultCallAdapterFactory,
        json: Json,
        igdbConstantsProvider: IgdbConstantsProvider,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory())
            .baseUrl(igdbConstantsProvider.apiBaseUrl)
            .build()
    }

    @Provides
    fun provideApicalypseQueryBuilderFactory(): ApicalypseQueryBuilderFactory {
        return ApicalypseQueryBuilderFactory
    }

    @Provides
    fun provideApicalypseSerializer(): ApicalypseSerializer {
        return ApicalypseSerializerFactory.create()
    }
}
