package ca.six.hojat.gamehub.gamespot.api.articles

import ca.six.hojat.gamehub.common.api.ApiResult
import ca.six.hojat.gamehub.gamespot.api.articles.entities.ApiArticle
import ca.six.hojat.gamehub.gamespot.api.common.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

internal interface ArticlesService {

    @GET("articles")
    suspend fun getArticles(
        @QueryMap queryParams: Map<String, String>,
    ): ApiResult<Response<ApiArticle>>
}
