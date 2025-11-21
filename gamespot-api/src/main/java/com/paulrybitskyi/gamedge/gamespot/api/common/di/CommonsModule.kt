package com.paulrybitskyi.gamedge.gamespot.api.common.di

import com.paulrybitskyi.gamedge.common.api.ErrorMessageExtractor
import com.paulrybitskyi.gamedge.common.api.addInterceptorAsFirstInChain
import com.paulrybitskyi.gamedge.common.api.calladapter.ApiResultCallAdapterFactory
import com.paulrybitskyi.gamedge.gamespot.api.common.GamespotConstantsProvider
import com.paulrybitskyi.gamedge.gamespot.api.common.GamespotQueryParamsFactory
import com.paulrybitskyi.gamedge.gamespot.api.common.GamespotQueryParamsFactoryImpl
import com.paulrybitskyi.gamedge.gamespot.api.common.UserAgentInterceptor
import com.paulrybitskyi.gamedge.gamespot.api.common.serialization.GamespotFieldsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CommonsModule {

    @Provides
    @Singleton
    @GamespotApi
    fun provideOkHttpClient(
        okHttpClient: OkHttpClient,
        userAgentInterceptor: UserAgentInterceptor,
    ): OkHttpClient {
        return okHttpClient.newBuilder()
            .addInterceptorAsFirstInChain(userAgentInterceptor)
            .build()
    }

    @Provides
    @GamespotApi
    fun provideApiResultCallAdapterFactory(
        @GamespotApi errorMessageExtractor: ErrorMessageExtractor,
    ): ApiResultCallAdapterFactory {
        return ApiResultCallAdapterFactory(errorMessageExtractor)
    }

    @Provides
    fun provideGamespotQueryParamsBuilder(
        gamespotFieldsSerializer: GamespotFieldsSerializer,
        gamespotConstantsProvider: GamespotConstantsProvider,
    ): GamespotQueryParamsFactory {
        return GamespotQueryParamsFactoryImpl(
            gamespotFieldsSerializer = gamespotFieldsSerializer,
            apiKey = gamespotConstantsProvider.apiKey,
        )
    }
}
