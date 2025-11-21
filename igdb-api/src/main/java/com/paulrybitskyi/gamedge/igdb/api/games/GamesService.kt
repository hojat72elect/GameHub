
package com.paulrybitskyi.gamedge.igdb.api.games

import com.paulrybitskyi.gamedge.common.api.ApiResult
import com.paulrybitskyi.gamedge.igdb.api.games.entities.ApiGame
import retrofit2.http.Body
import retrofit2.http.POST

internal interface GamesService {

    @POST("games")
    suspend fun getGames(@Body body: String): ApiResult<List<ApiGame>>
}
