package ca.six.hojat.gamehub.shared.data.local.di

import android.content.Context
import androidx.room.Room
import ca.six.hojat.gamehub.shared.data.local.Constants
import ca.six.hojat.gamehub.shared.data.local.GameHubDatabase
import ca.six.hojat.gamehub.shared.data.local.MANUAL_MIGRATIONS
import ca.six.hojat.gamehub.shared.data.local.RoomTypeConverter
import ca.six.hojat.gamehub.shared.data.local.authentication.tables.AuthenticationTable
import ca.six.hojat.gamehub.shared.data.local.games.tables.GamesRefreshingTimestampsTable
import ca.six.hojat.gamehub.shared.data.local.games.tables.GamesTable
import ca.six.hojat.gamehub.shared.data.local.games.tables.LikedGamesTable
import ca.six.hojat.gamehub.shared.data.local.news_articles.tables.NewsArticlesRefreshingTimestampsTable
import ca.six.hojat.gamehub.shared.data.local.news_articles.tables.NewsArticlesTable
import ca.six.hojat.gamehub.shared.data.local.settings.tables.SettingsTable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    @Suppress("SpreadOperator")
    fun provideGamedgeDatabase(
        @ApplicationContext context: Context,
        typeConverters: Set<@JvmSuppressWildcards RoomTypeConverter>,
    ): GameHubDatabase {
        return Room.databaseBuilder(
            context,
            GameHubDatabase::class.java,
            Constants.DATABASE_NAME,
        )
            .addTypeConverters(typeConverters)
            .addMigrations(*MANUAL_MIGRATIONS)
            .build()
    }

    @Provides
    fun provideGamesTable(gameHubDatabase: GameHubDatabase): GamesTable {
        return gameHubDatabase.gamesTable
    }

    @Provides
    fun provideLikedGamesTable(gameHubDatabase: GameHubDatabase): LikedGamesTable {
        return gameHubDatabase.likedGamesTable
    }

    @Provides
    fun provideGamesRefreshingTimestampsTable(gameHubDatabase: GameHubDatabase): GamesRefreshingTimestampsTable {
        return gameHubDatabase.gamesRefreshingTimestampsTable
    }

    @Provides
    fun provideArticlesTable(gameHubDatabase: GameHubDatabase): NewsArticlesTable {
        return gameHubDatabase.newsArticlesTable
    }

    @Provides
    fun provideArticlesRefreshingTimestampsTable(gameHubDatabase: GameHubDatabase): NewsArticlesRefreshingTimestampsTable {
        return gameHubDatabase.newsArticlesRefreshingTimestampsTable
    }

    @Provides
    fun provideSettingsTable(gameHubDatabase: GameHubDatabase): SettingsTable {
        return gameHubDatabase.settingsTable
    }

    @Provides
    fun provideAuthTable(gameHubDatabase: GameHubDatabase): AuthenticationTable {
        return gameHubDatabase.authenticationTable
    }
}