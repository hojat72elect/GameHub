
package com.paulrybitskyi.gamedge.database.articles.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paulrybitskyi.gamedge.database.articles.entities.DbArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticles(articles: List<DbArticle>)

    @Query(
        """
        SELECT * FROM ${DbArticle.Schema.TABLE_NAME}
        ORDER BY ${DbArticle.Schema.PUBLICATION_DATE} DESC
        LIMIT :offset, :limit
        """,
    )
    fun observeArticles(offset: Int, limit: Int): Flow<List<DbArticle>>
}
