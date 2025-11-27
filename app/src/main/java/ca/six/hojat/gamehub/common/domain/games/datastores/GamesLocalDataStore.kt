package ca.six.hojat.gamehub.common.domain.games.datastores

import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import ca.six.hojat.gamehub.common.domain.games.entities.Company
import ca.six.hojat.gamehub.common.domain.games.entities.Game
import kotlinx.coroutines.flow.Flow

interface GamesLocalDataStore {
    suspend fun saveGames(games: List<Game>)
    suspend fun getGame(id: Int): Game?
    suspend fun getCompanyDevelopedGames(company: Company, pagination: Pagination): List<Game>
    suspend fun getSimilarGames(game: Game, pagination: Pagination): List<Game>
    suspend fun searchGames(searchQuery: String, pagination: Pagination): List<Game>

    fun observePopularGames(pagination: Pagination): Flow<List<Game>>
    fun observeRecentlyReleasedGames(pagination: Pagination): Flow<List<Game>>
    fun observeComingSoonGames(pagination: Pagination): Flow<List<Game>>
    fun observeMostAnticipatedGames(pagination: Pagination): Flow<List<Game>>
}
