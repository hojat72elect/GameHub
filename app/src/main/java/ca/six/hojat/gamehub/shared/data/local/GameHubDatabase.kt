package ca.six.hojat.gamehub.shared.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ca.six.hojat.gamehub.shared.data.local.authentication.entities.LocalAuthenticationCredentials
import ca.six.hojat.gamehub.shared.data.local.authentication.tables.AuthenticationTable
import ca.six.hojat.gamehub.shared.data.local.games.GamesTypeConverter
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalGame
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalGamesRefreshingTimestamp
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalLikedGame
import ca.six.hojat.gamehub.shared.data.local.games.tables.GamesRefreshingTimestampsTable
import ca.six.hojat.gamehub.shared.data.local.games.tables.GamesTable
import ca.six.hojat.gamehub.shared.data.local.games.tables.LikedGamesTable
import ca.six.hojat.gamehub.shared.data.local.news_articles.NewsArticlesTypeConverter
import ca.six.hojat.gamehub.shared.data.local.news_articles.entities.LocalNewsArticle
import ca.six.hojat.gamehub.shared.data.local.news_articles.entities.LocalNewsArticlesRefreshingTimestamp
import ca.six.hojat.gamehub.shared.data.local.news_articles.tables.NewsArticlesRefreshingTimestampsTable
import ca.six.hojat.gamehub.shared.data.local.news_articles.tables.NewsArticlesTable
import ca.six.hojat.gamehub.shared.data.local.settings.entities.LocalSettings
import ca.six.hojat.gamehub.shared.data.local.settings.tables.SettingsTable

@Database(
    entities = [
        LocalGame::class,
        LocalLikedGame::class,
        LocalGamesRefreshingTimestamp::class,
        LocalNewsArticle::class,
        LocalNewsArticlesRefreshingTimestamp::class,
        LocalSettings::class,
        LocalAuthenticationCredentials::class,
    ],
    version = Constants.VERSION,
    exportSchema = false,
)
// Seems really strange that I have to specify this annotation here
// with custom provided type converters
@TypeConverters(
    GamesTypeConverter::class,
    NewsArticlesTypeConverter::class,
)
internal abstract class GameHubDatabase : RoomDatabase() {
    abstract val gamesTable: GamesTable
    abstract val likedGamesTable: LikedGamesTable
    abstract val gamesRefreshingTimestampsTable: GamesRefreshingTimestampsTable
    abstract val newsArticlesTable: NewsArticlesTable
    abstract val newsArticlesRefreshingTimestampsTable: NewsArticlesRefreshingTimestampsTable
    abstract val settingsTable: SettingsTable
    abstract val authenticationTable: AuthenticationTable
}
