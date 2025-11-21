
package com.paulrybitskyi.gamedge.common.domain.games.di

import com.paulrybitskyi.gamedge.common.domain.games.common.throttling.GamesRefreshingThrottlerKeyProvider
import com.paulrybitskyi.gamedge.common.domain.games.common.throttling.GamesRefreshingThrottlerKeyProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CoreModule {

    @Binds
    fun bindGamesRefreshingThrottlerKeyProvider(
        binding: GamesRefreshingThrottlerKeyProviderImpl,
    ): GamesRefreshingThrottlerKeyProvider
}
