package ca.six.hojat.gamehub.shared.data.local.games.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalGame
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalLikedGame
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedGamesTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLikedGame(likedGame: LocalLikedGame)

    @Query(
        """
        DELETE FROM ${LocalLikedGame.Schema.TABLE_NAME}
        WHERE ${LocalLikedGame.Schema.GAME_ID} = :gameId
        """,
    )
    suspend fun deleteLikedGame(gameId: Int)

    @Query(
        """
        SELECT COUNT(*) FROM ${LocalLikedGame.Schema.TABLE_NAME}
        WHERE ${LocalLikedGame.Schema.GAME_ID} = :gameId
        """,
    )
    suspend fun isGameLiked(gameId: Int): Boolean

    @Query(
        """
        SELECT COUNT(*) FROM ${LocalLikedGame.Schema.TABLE_NAME}
        WHERE ${LocalLikedGame.Schema.GAME_ID} = :gameId
        """,
    )
    fun observeGameLikeState(gameId: Int): Flow<Boolean>

    @Query(
        """
        SELECT g.* FROM ${LocalGame.Schema.TABLE_NAME} AS g
        INNER JOIN ${LocalLikedGame.Schema.TABLE_NAME} AS lg
        ON lg.${LocalLikedGame.Schema.GAME_ID} = g.${LocalGame.Schema.ID}
        ORDER BY lg.${LocalLikedGame.Schema.LIKE_TIMESTAMP} DESC
        LIMIT :offset, :limit
        """,
    )
    fun observeLikedGames(offset: Int, limit: Int): Flow<List<LocalGame>>
}
