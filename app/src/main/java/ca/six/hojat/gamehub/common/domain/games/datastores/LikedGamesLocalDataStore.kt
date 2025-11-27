package ca.six.hojat.gamehub.common.domain.games.datastores

import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import ca.six.hojat.gamehub.common.domain.games.entities.Game
import kotlinx.coroutines.flow.Flow

interface LikedGamesLocalDataStore {
    suspend fun likeGame(gameId: Int)
    suspend fun unlikeGame(gameId: Int)
    suspend fun isGameLiked(gameId: Int): Boolean

    fun observeGameLikeState(gameId: Int): Flow<Boolean>
    fun observeLikedGames(pagination: Pagination): Flow<List<Game>>
}
