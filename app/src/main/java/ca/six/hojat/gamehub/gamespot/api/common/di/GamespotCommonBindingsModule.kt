package ca.six.hojat.gamehub.gamespot.api.common.di

import ca.six.hojat.gamehub.common.api.ErrorMessageExtractor
import ca.six.hojat.gamehub.gamespot.api.common.GamespotErrorMessageExtractor
import ca.six.hojat.gamehub.gamespot.api.common.serialization.GamespotFieldsSerializer
import ca.six.hojat.gamehub.gamespot.api.common.serialization.GamespotFieldsSerializerImpl
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
