package ca.six.hojat.gamehub.common.data.games.datastores.igdb

import ca.six.hojat.gamehub.common.api.ApiResult
import ca.six.hojat.gamehub.common.data.common.ApiErrorMapper
import ca.six.hojat.gamehub.common.data.games.common.DiscoveryGamesReleaseDatesProvider
import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.DomainResult
import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import ca.six.hojat.gamehub.common.domain.games.datastores.GamesRemoteDataStore
import ca.six.hojat.gamehub.common.domain.games.entities.Company
import ca.six.hojat.gamehub.common.domain.games.entities.Game
import ca.six.hojat.gamehub.igdb.api.games.GamesEndpoint
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiGame
import ca.six.hojat.gamehub.igdb.api.games.requests.GetComingSoonGamesRequest
import ca.six.hojat.gamehub.igdb.api.games.requests.GetGamesRequest
import ca.six.hojat.gamehub.igdb.api.games.requests.GetMostAnticipatedGamesRequest
import ca.six.hojat.gamehub.igdb.api.games.requests.GetPopularGamesRequest
import ca.six.hojat.gamehub.igdb.api.games.requests.GetRecentlyReleasedGamesRequest
import ca.six.hojat.gamehub.igdb.api.games.requests.SearchGamesRequest
import com.github.michaelbull.result.mapEither
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GamesIgdbDataStore @Inject constructor(
    private val gamesEndpoint: GamesEndpoint,
    private val releaseDatesProvider: DiscoveryGamesReleaseDatesProvider,
    private val dispatcherProvider: DispatcherProvider,
    private val igdbGameMapper: IgdbGameMapper,
    private val apiErrorMapper: ApiErrorMapper,
) : GamesRemoteDataStore {

    override suspend fun searchGames(searchQuery: String, pagination: Pagination): DomainResult<List<Game>> {
        return gamesEndpoint
            .searchGames(
                SearchGamesRequest(
                    searchQuery = searchQuery,
                    offset = pagination.offset,
                    limit = pagination.limit,
                ),
            )
            .toDataStoreResult()
    }

    override suspend fun getPopularGames(pagination: Pagination): DomainResult<List<Game>> {
        return gamesEndpoint
            .getPopularGames(
                GetPopularGamesRequest(
                    minReleaseDateTimestamp = releaseDatesProvider.getPopularGamesMinReleaseDate(),
                    offset = pagination.offset,
                    limit = pagination.limit,
                ),
            )
            .toDataStoreResult()
    }

    override suspend fun getRecentlyReleasedGames(pagination: Pagination): DomainResult<List<Game>> {
        return gamesEndpoint
            .getRecentlyReleasedGames(
                GetRecentlyReleasedGamesRequest(
                    minReleaseDateTimestamp = releaseDatesProvider.getRecentlyReleasedGamesMinReleaseDate(),
                    maxReleaseDateTimestamp = releaseDatesProvider.getRecentlyReleasedGamesMaxReleaseDate(),
                    offset = pagination.offset,
                    limit = pagination.limit,
                ),
            )
            .toDataStoreResult()
    }

    override suspend fun getComingSoonGames(pagination: Pagination): DomainResult<List<Game>> {
        return gamesEndpoint
            .getComingSoonGames(
                GetComingSoonGamesRequest(
                    minReleaseDateTimestamp = releaseDatesProvider.getComingSoonGamesMinReleaseDate(),
                    offset = pagination.offset,
                    limit = pagination.limit,
                ),
            )
            .toDataStoreResult()
    }

    override suspend fun getMostAnticipatedGames(pagination: Pagination): DomainResult<List<Game>> {
        return gamesEndpoint
            .getMostAnticipatedGames(
                GetMostAnticipatedGamesRequest(
                    minReleaseDateTimestamp = releaseDatesProvider.getMostAnticipatedGamesMinReleaseDate(),
                    offset = pagination.offset,
                    limit = pagination.limit,
                ),
            )
            .toDataStoreResult()
    }

    override suspend fun getCompanyDevelopedGames(
        company: Company,
        pagination: Pagination,
    ): DomainResult<List<Game>> {
        return gamesEndpoint
            .getGames(
                GetGamesRequest(
                    gameIds = company.developedGames,
                    offset = pagination.offset,
                    limit = pagination.limit,
                ),
            )
            .toDataStoreResult()
    }

    override suspend fun getSimilarGames(game: Game, pagination: Pagination): DomainResult<List<Game>> {
        return gamesEndpoint
            .getGames(
                GetGamesRequest(
                    gameIds = game.similarGames,
                    offset = pagination.offset,
                    limit = pagination.limit,
                ),
            )
            .toDataStoreResult()
    }

    private suspend fun ApiResult<List<ApiGame>>.toDataStoreResult(): DomainResult<List<Game>> {
        return withContext(dispatcherProvider.computation) {
            mapEither(igdbGameMapper::mapToDomainGames, apiErrorMapper::mapToDomainError)
        }
    }
}
