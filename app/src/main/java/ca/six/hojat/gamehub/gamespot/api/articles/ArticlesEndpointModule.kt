package ca.six.hojat.gamehub.gamespot.api.articles

import ca.six.hojat.gamehub.common.api.asConverterFactory
import ca.six.hojat.gamehub.common.api.calladapter.ApiResultCallAdapterFactory
import ca.six.hojat.gamehub.gamespot.api.common.GamespotConstantsProvider
import ca.six.hojat.gamehub.gamespot.api.common.di.Endpoint
import ca.six.hojat.gamehub.gamespot.api.common.di.GamespotApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object ArticlesEndpointModule {

    @Provides
    fun provideArticlesService(@Endpoint(Endpoint.Type.ARTICLES) retrofit: Retrofit): ArticlesService {
        return retrofit.create(ArticlesService::class.java)
    }

    @Provides
    @Endpoint(Endpoint.Type.ARTICLES)
    fun provideRetrofit(
        @GamespotApi okHttpClient: OkHttpClient,
        @GamespotApi callAdapterFactory: ApiResultCallAdapterFactory,
        json: Json,
        gamespotConstantsProvider: GamespotConstantsProvider,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(json.asConverterFactory())
            .baseUrl(gamespotConstantsProvider.apiBaseUrl)
            .build()
    }
}
