package ca.six.hojat.gamehub.database.common.di

import ca.six.hojat.gamehub.database.GamedgeDatabase
import ca.six.hojat.gamehub.database.articles.tables.ArticlesTable
import ca.six.hojat.gamehub.database.games.tables.GamesTable
import ca.six.hojat.gamehub.database.games.tables.LikedGamesTable
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
