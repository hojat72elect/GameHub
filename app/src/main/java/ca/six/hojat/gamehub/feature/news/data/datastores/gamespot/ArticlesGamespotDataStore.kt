package ca.six.hojat.gamehub.feature.news.data.datastores.gamespot

import ca.six.hojat.gamehub.common.api.ApiResult
import ca.six.hojat.gamehub.common.data.common.ApiErrorMapper
import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.DomainResult
import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import ca.six.hojat.gamehub.feature.news.domain.datastores.ArticlesRemoteDataStore
import ca.six.hojat.gamehub.feature.news.domain.entities.Article
import ca.six.hojat.gamehub.gamespot.api.articles.ArticlesEndpoint
import ca.six.hojat.gamehub.gamespot.api.articles.entities.ApiArticle
import com.github.michaelbull.result.mapEither
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ArticlesGamespotDataStoreImpl @Inject constructor(
    private val articlesEndpoint: ArticlesEndpoint,
    private val dispatcherProvider: DispatcherProvider,
    private val apiArticleMapper: GamespotArticleMapper,
    private val apiErrorMapper: ApiErrorMapper,
) : ArticlesRemoteDataStore {

    override suspend fun getArticles(pagination: Pagination): DomainResult<List<Article>> {
        return articlesEndpoint
            .getArticles(pagination.offset, pagination.limit)
            .toDataStoreResult()
    }

    private suspend fun ApiResult<List<ApiArticle>>.toDataStoreResult(): DomainResult<List<Article>> {
        return withContext(dispatcherProvider.computation) {
            mapEither(apiArticleMapper::mapToDomainArticles, apiErrorMapper::mapToDomainError)
        }
    }
}
