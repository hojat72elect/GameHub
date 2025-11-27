package ca.six.hojat.gamehub.gamespot.api.common.di

import ca.six.hojat.gamehub.gamespot.api.common.GamespotConstantsProvider
import ca.six.hojat.gamehub.gamespot.api.common.ProdGamespotConstantsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface GamespotConstantsModule {

    @Binds
    fun bindGamespotConstantsProvider(binding: ProdGamespotConstantsProvider): GamespotConstantsProvider
}
