package com.paulrybitskyi.gamedge.database.common.di

import com.paulrybitskyi.gamedge.database.GamedgeDatabase
import com.paulrybitskyi.gamedge.database.articles.tables.ArticlesTable
import com.paulrybitskyi.gamedge.database.games.tables.GamesTable
import com.paulrybitskyi.gamedge.database.games.tables.LikedGamesTable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TablesModule {

    @Provides
    @Singleton
    fun provideGamesTable(gamedgeDatabase: GamedgeDatabase): GamesTable {
        return gamedgeDatabase.gamesTable
    }

    @Provides
    @Singleton
    fun provideLikedGamesTable(gamedgeDatabase: GamedgeDatabase): LikedGamesTable {
        return gamedgeDatabase.likedGamesTable
    }

    @Provides
    @Singleton
    fun provideArticlesTable(gamedgeDatabase: GamedgeDatabase): ArticlesTable {
        return gamedgeDatabase.articlesTable
    }
}
