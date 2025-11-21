package com.paulrybitskyi.gamedge.gamespot.api.articles

import com.paulrybitskyi.gamedge.common.api.asConverterFactory
import com.paulrybitskyi.gamedge.common.api.calladapter.ApiResultCallAdapterFactory
import com.paulrybitskyi.gamedge.gamespot.api.common.GamespotConstantsProvider
import com.paulrybitskyi.gamedge.gamespot.api.common.di.Endpoint
import com.paulrybitskyi.gamedge.gamespot.api.common.di.GamespotApi
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
