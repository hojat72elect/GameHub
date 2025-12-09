package ca.six.hojat.gamehub.shared.data.local.news_articles.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.six.hojat.gamehub.shared.data.local.news_articles.entities.LocalNewsArticlesRefreshingTimestamp

@Dao
interface NewsArticlesRefreshingTimestampsTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(timestamp: LocalNewsArticlesRefreshingTimestamp)

    @Query("SELECT * FROM articles_refreshing_timestamps WHERE `key` = :key")
    suspend fun get(key: String): LocalNewsArticlesRefreshingTimestamp?
}
