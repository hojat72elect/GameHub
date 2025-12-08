package ca.six.hojat.gamehub.database.games.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.six.hojat.gamehub.database.games.entities.DbGamesRefreshingTimestamp

@Dao
interface GamesRefreshingTimestampsTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(timestamp: DbGamesRefreshingTimestamp)

    @Query("SELECT * FROM games_refreshing_timestamps WHERE `key` = :key")
    suspend fun get(key: String): DbGamesRefreshingTimestamp?
}
