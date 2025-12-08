package ca.six.hojat.gamehub.database.articles.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles_refreshing_timestamps")
data class DbArticlesRefreshingTimestamp(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "last_refresh_timestamp")
    val lastRefreshTimestamp: Long,
)
