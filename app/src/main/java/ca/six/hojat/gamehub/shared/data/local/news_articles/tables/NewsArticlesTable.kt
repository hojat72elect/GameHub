package ca.six.hojat.gamehub.shared.data.local.news_articles.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.six.hojat.gamehub.shared.data.local.news_articles.entities.LocalNewsArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticlesTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticles(articles: List<LocalNewsArticle>)

    @Query(
        """
        SELECT * FROM ${LocalNewsArticle.Schema.TABLE_NAME}
        ORDER BY ${LocalNewsArticle.Schema.PUBLICATION_DATE} DESC
        LIMIT :offset, :limit
        """,
    )
    fun observeArticles(offset: Int, limit: Int): Flow<List<LocalNewsArticle>>
}
