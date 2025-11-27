package ca.six.hojat.gamehub.igdb.api.games

import ca.six.hojat.gamehub.common.api.ApiResult
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiGame
import ca.six.hojat.gamehub.igdb.api.games.requests.GetComingSoonGamesRequest
import ca.six.hojat.gamehub.igdb.api.games.requests.GetGamesRequest
import ca.six.hojat.gamehub.igdb.api.games.requests.GetMostAnticipatedGamesRequest
import ca.six.hojat.gamehub.igdb.api.games.requests.GetPopularGamesRequest
import ca.six.hojat.gamehub.igdb.api.games.requests.GetRecentlyReleasedGamesRequest
import ca.six.hojat.gamehub.igdb.api.games.requests.SearchGamesRequest
import javax.inject.Inject
import javax.inject.Singleton

interface GamesEndpoint {
    suspend fun searchGames(request: SearchGamesRequest): ApiResult<List<ApiGame>>
    suspend fun getPopularGames(request: GetPopularGamesRequest): ApiResult<List<ApiGame>>
    suspend fun getRecentlyReleasedGames(request: GetRecentlyReleasedGamesRequest): ApiResult<List<ApiGame>>
    suspend fun getComingSoonGames(request: GetComingSoonGamesRequest): ApiResult<List<ApiGame>>
    suspend fun getMostAnticipatedGames(request: GetMostAnticipatedGamesRequest): ApiResult<List<ApiGame>>
    suspend fun getGames(request: GetGamesRequest): ApiResult<List<ApiGame>>
}

@Singleton
internal class GamesEndpointImpl @Inject constructor(
    private val gamesService: GamesService,
    private val igdbApiQueryFactory: IgdbApiQueryFactory,
) : GamesEndpoint {

    override suspend fun searchGames(request: SearchGamesRequest): ApiResult<List<ApiGame>> {
        return gamesService.getGames(
            igdbApiQueryFactory.createGamesSearchingQuery(request),
        )
    }

    override suspend fun getPopularGames(request: GetPopularGamesRequest): ApiResult<List<ApiGame>> {
        return gamesService.getGames(
            igdbApiQueryFactory.createPopularGamesRetrievalQuery(request),
        )
    }

    override suspend fun getRecentlyReleasedGames(
        request: GetRecentlyReleasedGamesRequest,
    ): ApiResult<List<ApiGame>> {
        return gamesService.getGames(
            igdbApiQueryFactory.createRecentlyReleasedGamesRetrievalQuery(request),
        )
    }

    override suspend fun getComingSoonGames(request: GetComingSoonGamesRequest): ApiResult<List<ApiGame>> {
        return gamesService.getGames(
            igdbApiQueryFactory.createComingSoonGamesRetrievalQuery(request),
        )
    }

    override suspend fun getMostAnticipatedGames(
        request: GetMostAnticipatedGamesRequest,
    ): ApiResult<List<ApiGame>> {
        return gamesService.getGames(
            igdbApiQueryFactory.createMostAnticipatedGamesRetrievalQuery(request),
        )
    }

    override suspend fun getGames(request: GetGamesRequest): ApiResult<List<ApiGame>> {
        return gamesService.getGames(
            igdbApiQueryFactory.createGamesRetrievalQuery(request),
        )
    }
}
