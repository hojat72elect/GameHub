package ca.six.hojat.gamehub.igdb.api.games

import ca.six.hojat.gamehub.common.api.ApiResult
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiGame
import retrofit2.http.Body
import retrofit2.http.POST

internal interface GamesService {

    @POST("games")
    suspend fun getGames(@Body body: String): ApiResult<List<ApiGame>>
}
