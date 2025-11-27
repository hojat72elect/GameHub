package ca.six.hojat.gamehub.igdb.api.common.di

import ca.six.hojat.gamehub.igdb.api.common.IgdbConstantsProvider
import ca.six.hojat.gamehub.igdb.api.common.ProdIgdbConstantsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface IgdbConstantsModule {

    @Binds
    fun bindIgdbConstantsProvider(binding: ProdIgdbConstantsProvider): IgdbConstantsProvider
}
