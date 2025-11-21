
package com.paulrybitskyi.gamedge.gamespot.api.common.di

import com.paulrybitskyi.gamedge.gamespot.api.common.GamespotConstantsProvider
import com.paulrybitskyi.gamedge.gamespot.api.common.ProdGamespotConstantsProvider
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
