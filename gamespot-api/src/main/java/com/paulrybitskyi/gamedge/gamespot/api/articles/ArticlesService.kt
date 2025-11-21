
package com.paulrybitskyi.gamedge.gamespot.api.articles

import com.paulrybitskyi.gamedge.common.api.ApiResult
import com.paulrybitskyi.gamedge.gamespot.api.articles.entities.ApiArticle
import com.paulrybitskyi.gamedge.gamespot.api.common.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

internal interface ArticlesService {

    @GET("articles")
    suspend fun getArticles(
        @QueryMap queryParams: Map<String, String>,
    ): ApiResult<Response<ApiArticle>>
}
