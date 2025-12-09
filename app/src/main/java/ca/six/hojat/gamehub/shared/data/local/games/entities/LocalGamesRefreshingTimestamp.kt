package ca.six.hojat.gamehub.shared.data.local.games.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games_refreshing_timestamps")
data class LocalGamesRefreshingTimestamp(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "last_refresh_timestamp")
    val lastRefreshTimestamp: Long,
)
