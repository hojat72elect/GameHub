package ca.six.hojat.gamehub.database.common.di

import android.content.Context
import androidx.room.Room
import ca.six.hojat.gamehub.database.Constants
import ca.six.hojat.gamehub.database.GamedgeDatabase
import ca.six.hojat.gamehub.database.common.MANUAL_MIGRATIONS
import ca.six.hojat.gamehub.database.common.RoomTypeConverter
import ca.six.hojat.gamehub.database.common.addTypeConverters
import ca.six.hojat.gamehub.shared.data.local.auth.tables.AuthTable
import ca.six.hojat.gamehub.shared.data.local.games.tables.GamesRefreshingTimestampsTable
import ca.six.hojat.gamehub.shared.data.local.games.tables.GamesTable
import ca.six.hojat.gamehub.shared.data.local.games.tables.LikedGamesTable
import ca.six.hojat.gamehub.shared.data.local.news_articles.tables.ArticlesRefreshingTimestampsTable
import ca.six.hojat.gamehub.shared.data.local.news_articles.tables.ArticlesTable
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
    ): GamedgeDatabase {
        return Room.databaseBuilder(
            context,
            GamedgeDatabase::class.java,
            Constants.DATABASE_NAME,
        )
            .addTypeConverters(typeConverters)
            .addMigrations(*MANUAL_MIGRATIONS)
            .build()
    }

    @Provides
    fun provideGamesTable(gamedgeDatabase: GamedgeDatabase): GamesTable {
        return gamedgeDatabase.gamesTable
    }

    @Provides
    fun provideLikedGamesTable(gamedgeDatabase: GamedgeDatabase): LikedGamesTable {
        return gamedgeDatabase.likedGamesTable
    }

    @Provides
    fun provideGamesRefreshingTimestampsTable(gamedgeDatabase: GamedgeDatabase): GamesRefreshingTimestampsTable {
        return gamedgeDatabase.gamesRefreshingTimestampsTable
    }

    @Provides
    fun provideArticlesTable(gamedgeDatabase: GamedgeDatabase): ArticlesTable {
        return gamedgeDatabase.articlesTable
    }

    @Provides
    fun provideArticlesRefreshingTimestampsTable(gamedgeDatabase: GamedgeDatabase): ArticlesRefreshingTimestampsTable {
        return gamedgeDatabase.articlesRefreshingTimestampsTable
    }

    @Provides
    fun provideSettingsTable(gamedgeDatabase: GamedgeDatabase): SettingsTable {
        return gamedgeDatabase.settingsTable
    }

    @Provides
    fun provideAuthTable(gamedgeDatabase: GamedgeDatabase): AuthTable {
        return gamedgeDatabase.authTable
    }
}
