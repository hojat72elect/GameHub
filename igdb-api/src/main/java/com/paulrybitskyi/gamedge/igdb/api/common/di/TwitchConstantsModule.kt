
package com.paulrybitskyi.gamedge.igdb.api.common.di

import com.paulrybitskyi.gamedge.igdb.api.common.ProdTwitchConstantsProvider
import com.paulrybitskyi.gamedge.igdb.api.common.TwitchConstantsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface TwitchConstantsModule {

    @Binds
    fun bindTwitchConstantsProvider(binding: ProdTwitchConstantsProvider): TwitchConstantsProvider
}
