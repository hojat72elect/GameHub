package ca.six.hojat.gamehub.feature.news.domain.datastores

import ca.six.hojat.gamehub.common.domain.common.DomainResult
import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import ca.six.hojat.gamehub.feature.news.domain.entities.Article

internal interface ArticlesRemoteDataStore {
    suspend fun getArticles(pagination: Pagination): DomainResult<List<Article>>
}
