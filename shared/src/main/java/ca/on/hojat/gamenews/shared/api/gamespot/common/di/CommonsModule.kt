package ca.on.hojat.gamenews.shared.api.gamespot.common.di

import ca.on.hojat.gamenews.shared.api.common.ErrorMessageExtractor
import ca.on.hojat.gamenews.shared.api.common.addInterceptorAsFirstInChain
import ca.on.hojat.gamenews.shared.api.common.calladapter.ApiResultCallAdapterFactory
import ca.on.hojat.gamenews.shared.api.gamespot.common.GamespotConstantsProvider
import ca.on.hojat.gamenews.shared.api.gamespot.common.GamespotQueryParamsFactory
import ca.on.hojat.gamenews.shared.api.gamespot.common.GamespotQueryParamsFactoryImpl
import ca.on.hojat.gamenews.shared.api.gamespot.common.UserAgentInterceptor
import ca.on.hojat.gamenews.shared.api.gamespot.common.serialization.GamespotFieldsSerializer
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
        userAgentInterceptor: UserAgentInterceptor
    ): OkHttpClient {
        return okHttpClient.newBuilder()
            .addInterceptorAsFirstInChain(userAgentInterceptor)
            .build()
    }

    @Provides
    @GamespotApi
    fun provideApiResultCallAdapterFactory(
        @GamespotApi errorMessageExtractor: ErrorMessageExtractor
    ): ApiResultCallAdapterFactory {
        return ApiResultCallAdapterFactory(errorMessageExtractor)
    }

    @Provides
    fun provideGamespotQueryParamsBuilder(
        gamespotFieldsSerializer: GamespotFieldsSerializer,
        gamespotConstantsProvider: GamespotConstantsProvider
    ): GamespotQueryParamsFactory {
        return GamespotQueryParamsFactoryImpl(
            gamespotFieldsSerializer = gamespotFieldsSerializer,
            apiKey = gamespotConstantsProvider.apiKey
        )
    }
}