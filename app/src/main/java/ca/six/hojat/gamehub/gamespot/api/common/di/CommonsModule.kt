package ca.six.hojat.gamehub.gamespot.api.common.di

import ca.six.hojat.gamehub.common.api.ErrorMessageExtractor
import ca.six.hojat.gamehub.common.api.addInterceptorAsFirstInChain
import ca.six.hojat.gamehub.common.api.calladapter.ApiResultCallAdapterFactory
import ca.six.hojat.gamehub.gamespot.api.common.GamespotConstantsProvider
import ca.six.hojat.gamehub.gamespot.api.common.GamespotQueryParamsFactory
import ca.six.hojat.gamehub.gamespot.api.common.GamespotQueryParamsFactoryImpl
import ca.six.hojat.gamehub.gamespot.api.common.UserAgentInterceptor
import ca.six.hojat.gamehub.gamespot.api.common.serialization.GamespotFieldsSerializer
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
