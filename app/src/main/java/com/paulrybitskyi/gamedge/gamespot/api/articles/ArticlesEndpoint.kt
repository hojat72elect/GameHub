package com.paulrybitskyi.gamedge.gamespot.api.articles

import com.github.michaelbull.result.map
import com.paulrybitskyi.gamedge.common.api.ApiResult
import com.paulrybitskyi.gamedge.gamespot.api.articles.entities.ApiArticle
import com.paulrybitskyi.gamedge.gamespot.api.common.GamespotQueryParamsFactory
import com.paulrybitskyi.gamedge.gamespot.api.common.QUERY_PARAM_LIMIT
import com.paulrybitskyi.gamedge.gamespot.api.common.QUERY_PARAM_OFFSET
import com.paulrybitskyi.gamedge.gamespot.api.common.Response
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
