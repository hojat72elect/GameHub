package com.paulrybitskyi.gamedge.gamespot.api.common.di

import com.paulrybitskyi.gamedge.common.api.ErrorMessageExtractor
import com.paulrybitskyi.gamedge.gamespot.api.common.GamespotErrorMessageExtractor
import com.paulrybitskyi.gamedge.gamespot.api.common.serialization.GamespotFieldsSerializer
import com.paulrybitskyi.gamedge.gamespot.api.common.serialization.GamespotFieldsSerializerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface GamespotCommonBindingsModule {

    @Binds
    @GamespotApi
    fun bindGamespotErrorMessageExtractor(binding: GamespotErrorMessageExtractor): ErrorMessageExtractor

    @Binds
    fun bindGamespotFieldsSerializer(binding: GamespotFieldsSerializerImpl): GamespotFieldsSerializer
}
