
package com.paulrybitskyi.gamedge.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paulrybitskyi.gamedge.database.articles.ArticlesTypeConverter
import com.paulrybitskyi.gamedge.database.articles.entities.DbArticle
import com.paulrybitskyi.gamedge.database.articles.tables.ArticlesTable
import com.paulrybitskyi.gamedge.database.common.MIGRATION_FROM_2_TO_3_SPEC
import com.paulrybitskyi.gamedge.database.games.GamesTypeConverter
import com.paulrybitskyi.gamedge.database.games.entities.DbGame
import com.paulrybitskyi.gamedge.database.games.entities.DbLikedGame
import com.paulrybitskyi.gamedge.database.games.tables.GamesTable
import com.paulrybitskyi.gamedge.database.games.tables.LikedGamesTable

@Database(
    entities = [
        DbGame::class,
        DbLikedGame::class,
        DbArticle::class,
    ],
    version = Constants.VERSION,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 2, to = 3, MIGRATION_FROM_2_TO_3_SPEC::class),
    ],
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
    abstract val articlesTable: ArticlesTable
}
