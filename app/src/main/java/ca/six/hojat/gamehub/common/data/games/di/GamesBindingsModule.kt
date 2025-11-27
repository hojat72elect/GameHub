package ca.six.hojat.gamehub.common.data.games.di

import ca.six.hojat.gamehub.common.data.games.common.DiscoveryGamesReleaseDatesProvider
import ca.six.hojat.gamehub.common.data.games.common.DiscoveryGamesReleaseDatesProviderImpl
import ca.six.hojat.gamehub.common.data.games.common.throttling.GamesRefreshingThrottlerImpl
import ca.six.hojat.gamehub.common.data.games.datastores.database.GamesDatabaseDataStore
import ca.six.hojat.gamehub.common.data.games.datastores.database.LikedGameFactory
import ca.six.hojat.gamehub.common.data.games.datastores.database.LikedGameFactoryImpl
import ca.six.hojat.gamehub.common.data.games.datastores.database.LikedGamesDatabaseDataStore
import ca.six.hojat.gamehub.common.data.games.datastores.igdb.GamesIgdbDataStore
import ca.six.hojat.gamehub.common.domain.games.common.throttling.GamesRefreshingThrottler
import ca.six.hojat.gamehub.common.domain.games.datastores.GamesLocalDataStore
import ca.six.hojat.gamehub.common.domain.games.datastores.GamesRemoteDataStore
import ca.six.hojat.gamehub.common.domain.games.datastores.LikedGamesLocalDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface GamesBindingsModule {

    @Binds
    @Singleton
    fun bindGamesLocalDataStore(binding: GamesDatabaseDataStore): GamesLocalDataStore

    @Binds
    @Singleton
    fun bindGamesRemoteDataStore(binding: GamesIgdbDataStore): GamesRemoteDataStore

    @Binds
    @Singleton
    fun bindLikedGamesLocalDataStore(binding: LikedGamesDatabaseDataStore): LikedGamesLocalDataStore

    @Binds
    @Singleton
    fun bindGamesRefreshingThrottler(binding: GamesRefreshingThrottlerImpl): GamesRefreshingThrottler

    @Binds
    @Singleton
    fun bindDiscoveryGamesReleaseDatesProvider(
        binding: DiscoveryGamesReleaseDatesProviderImpl,
    ): DiscoveryGamesReleaseDatesProvider

    @Binds
    @Singleton
    fun bindLikedGameFactory(binding: LikedGameFactoryImpl): LikedGameFactory
}
