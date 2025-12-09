package ca.six.hojat.gamehub.shared.data.local.games.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalGamesRefreshingTimestamp

@Dao
interface GamesRefreshingTimestampsTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(timestamp: LocalGamesRefreshingTimestamp)

    @Query("SELECT * FROM games_refreshing_timestamps WHERE `key` = :key")
    suspend fun get(key: String): LocalGamesRefreshingTimestamp?
}
