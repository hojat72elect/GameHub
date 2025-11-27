package ca.six.hojat.gamehub.gamespot.api.articles

import ca.six.hojat.gamehub.common.api.ApiResult
import ca.six.hojat.gamehub.gamespot.api.articles.entities.ApiArticle
import ca.six.hojat.gamehub.gamespot.api.common.GamespotQueryParamsFactory
import ca.six.hojat.gamehub.gamespot.api.common.QUERY_PARAM_LIMIT
import ca.six.hojat.gamehub.gamespot.api.common.QUERY_PARAM_OFFSET
import ca.six.hojat.gamehub.gamespot.api.common.Response
import com.github.michaelbull.result.map
import javax.inject.Inject
import javax.inject.Singleton

interface ArticlesEndpoint {
    suspend fun getArticles(offset: Int, limit: Int): ApiResult<List<ApiArticle>>
}

@Singleton
internal class ArticlesEndpointImpl @Inject constructor(
    private val articlesService: ArticlesService,
    private val queryParamsFactory: GamespotQueryParamsFactory,
) : ArticlesEndpoint {

    override suspend fun getArticles(offset: Int, limit: Int): ApiResult<List<ApiArticle>> {
        val queryParams = queryParamsFactory.createArticlesQueryParams {
            put(QUERY_PARAM_OFFSET, offset.toString())
            put(QUERY_PARAM_LIMIT, limit.toString())
        }

        return articlesService.getArticles(queryParams)
            .map(Response<ApiArticle>::results)
    }
}
