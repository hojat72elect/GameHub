package ca.six.hojat.gamehub.shared.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games_refreshing_timestamps")
data class DbGamesRefreshingTimestamp(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "last_refresh_timestamp")
    val lastRefreshTimestamp: Long,
)
