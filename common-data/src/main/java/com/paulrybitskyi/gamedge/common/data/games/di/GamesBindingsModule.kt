package com.paulrybitskyi.gamedge.common.data.games.di

import com.paulrybitskyi.gamedge.common.data.games.common.DiscoveryGamesReleaseDatesProvider
import com.paulrybitskyi.gamedge.common.data.games.common.DiscoveryGamesReleaseDatesProviderImpl
import com.paulrybitskyi.gamedge.common.data.games.common.throttling.GamesRefreshingThrottlerImpl
import com.paulrybitskyi.gamedge.common.data.games.datastores.database.GamesDatabaseDataStore
import com.paulrybitskyi.gamedge.common.data.games.datastores.database.LikedGameFactory
import com.paulrybitskyi.gamedge.common.data.games.datastores.database.LikedGameFactoryImpl
import com.paulrybitskyi.gamedge.common.data.games.datastores.database.LikedGamesDatabaseDataStore
import com.paulrybitskyi.gamedge.common.data.games.datastores.igdb.GamesIgdbDataStore
import com.paulrybitskyi.gamedge.common.domain.games.common.throttling.GamesRefreshingThrottler
import com.paulrybitskyi.gamedge.common.domain.games.datastores.GamesLocalDataStore
import com.paulrybitskyi.gamedge.common.domain.games.datastores.GamesRemoteDataStore
import com.paulrybitskyi.gamedge.common.domain.games.datastores.LikedGamesLocalDataStore
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
