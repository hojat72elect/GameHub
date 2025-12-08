package ca.six.hojat.gamehub.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ca.six.hojat.gamehub.database.articles.ArticlesTypeConverter
import ca.six.hojat.gamehub.database.articles.entities.DbArticle
import ca.six.hojat.gamehub.database.articles.entities.DbArticlesRefreshingTimestamp
import ca.six.hojat.gamehub.database.articles.tables.ArticlesRefreshingTimestampsTable
import ca.six.hojat.gamehub.database.articles.tables.ArticlesTable
import ca.six.hojat.gamehub.database.auth.entities.DbOauthCredentials
import ca.six.hojat.gamehub.database.auth.tables.AuthTable
import ca.six.hojat.gamehub.database.games.GamesTypeConverter
import ca.six.hojat.gamehub.database.games.entities.DbGame
import ca.six.hojat.gamehub.database.games.entities.DbGamesRefreshingTimestamp
import ca.six.hojat.gamehub.database.games.entities.DbLikedGame
import ca.six.hojat.gamehub.database.games.tables.GamesRefreshingTimestampsTable
import ca.six.hojat.gamehub.database.games.tables.GamesTable
import ca.six.hojat.gamehub.database.games.tables.LikedGamesTable
import ca.six.hojat.gamehub.database.settings.entities.DbSettings
import ca.six.hojat.gamehub.database.settings.tables.SettingsTable

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
