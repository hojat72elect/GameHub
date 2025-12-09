package ca.six.hojat.gamehub.shared.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ca.six.hojat.gamehub.shared.data.local.auth.entities.DbOauthCredentials
import ca.six.hojat.gamehub.shared.data.local.auth.tables.AuthTable
import ca.six.hojat.gamehub.shared.data.local.games.GamesTypeConverter
import ca.six.hojat.gamehub.shared.data.local.games.entities.DbGame
import ca.six.hojat.gamehub.shared.data.local.games.entities.DbGamesRefreshingTimestamp
import ca.six.hojat.gamehub.shared.data.local.games.entities.DbLikedGame
import ca.six.hojat.gamehub.shared.data.local.games.tables.GamesRefreshingTimestampsTable
import ca.six.hojat.gamehub.shared.data.local.games.tables.GamesTable
import ca.six.hojat.gamehub.shared.data.local.games.tables.LikedGamesTable
import ca.six.hojat.gamehub.shared.data.local.news_articles.ArticlesTypeConverter
import ca.six.hojat.gamehub.shared.data.local.news_articles.entities.DbArticle
import ca.six.hojat.gamehub.shared.data.local.news_articles.entities.DbArticlesRefreshingTimestamp
import ca.six.hojat.gamehub.shared.data.local.news_articles.tables.ArticlesRefreshingTimestampsTable
import ca.six.hojat.gamehub.shared.data.local.news_articles.tables.ArticlesTable
import ca.six.hojat.gamehub.shared.data.local.settings.entities.DbSettings
import ca.six.hojat.gamehub.shared.data.local.settings.tables.SettingsTable

@Database(
    entities = [
        DbGame::class,
        DbLikedGame::class,
        DbGamesRefreshingTimestamp::class,
        DbArticle::class,
        DbArticlesRefreshingTimestamp::class,
        DbSettings::class,
        DbOauthCredentials::class,
    ],
    version = Constants.VERSION,
    exportSchema = false,
)
// Seems really strange that I have to specify this annotation here
// with custom provided type converters
@TypeConverters(
    GamesTypeConverter::class,
    ArticlesTypeConverter::class,
)
internal abstract class GamedgeDatabase : RoomDatabase() {
    abstract val gamesTable: GamesTable
    abstract val likedGamesTable: LikedGamesTable
    abstract val gamesRefreshingTimestampsTable: GamesRefreshingTimestampsTable
    abstract val articlesTable: ArticlesTable
    abstract val articlesRefreshingTimestampsTable: ArticlesRefreshingTimestampsTable
    abstract val settingsTable: SettingsTable
    abstract val authTable: AuthTable
}
