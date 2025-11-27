package ca.six.hojat.gamehub.common.domain.games.di

import ca.six.hojat.gamehub.common.domain.games.common.throttling.GamesRefreshingThrottlerKeyProvider
import ca.six.hojat.gamehub.common.domain.games.common.throttling.GamesRefreshingThrottlerKeyProviderImpl
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
