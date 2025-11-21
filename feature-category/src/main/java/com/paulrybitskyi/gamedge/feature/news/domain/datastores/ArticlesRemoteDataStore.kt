package com.paulrybitskyi.gamedge.feature.news.domain.datastores

import com.paulrybitskyi.gamedge.common.domain.common.DomainResult
import com.paulrybitskyi.gamedge.common.domain.common.entities.Pagination
import com.paulrybitskyi.gamedge.feature.news.domain.entities.Article

internal interface ArticlesRemoteDataStore {
    suspend fun getArticles(pagination: Pagination): DomainResult<List<Article>>
}
