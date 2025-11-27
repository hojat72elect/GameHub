package ca.six.hojat.gamehub.feature.news.domain.datastores

import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import ca.six.hojat.gamehub.feature.news.domain.entities.Article
import kotlinx.coroutines.flow.Flow

internal interface ArticlesLocalDataStore {
    suspend fun saveArticles(articles: List<Article>)
    fun observeArticles(pagination: Pagination): Flow<List<Article>>
}
