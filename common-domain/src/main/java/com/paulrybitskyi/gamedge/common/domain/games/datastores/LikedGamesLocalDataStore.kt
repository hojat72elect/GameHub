package com.paulrybitskyi.gamedge.common.domain.games.datastores

import com.paulrybitskyi.gamedge.common.domain.common.entities.Pagination
import com.paulrybitskyi.gamedge.common.domain.games.entities.Game
import kotlinx.coroutines.flow.Flow

interface LikedGamesLocalDataStore {
    suspend fun likeGame(gameId: Int)
    suspend fun unlikeGame(gameId: Int)
    suspend fun isGameLiked(gameId: Int): Boolean

    fun observeGameLikeState(gameId: Int): Flow<Boolean>
    fun observeLikedGames(pagination: Pagination): Flow<List<Game>>
}
