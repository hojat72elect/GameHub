package com.paulrybitskyi.gamedge.feature.news.domain.datastores

import com.paulrybitskyi.gamedge.common.domain.common.entities.Pagination
import com.paulrybitskyi.gamedge.feature.news.domain.entities.Article
import kotlinx.coroutines.flow.Flow

internal interface ArticlesLocalDataStore {
    suspend fun saveArticles(articles: List<Article>)
    fun observeArticles(pagination: Pagination): Flow<List<Article>>
}
