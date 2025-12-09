package ca.six.hojat.gamehub.shared.data.local.games.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.six.hojat.gamehub.shared.data.local.games.entities.DbGame
import ca.six.hojat.gamehub.shared.data.local.games.entities.DbLikedGame
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedGamesTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLikedGame(likedGame: DbLikedGame)

    @Query(
        """
        DELETE FROM ${DbLikedGame.Schema.TABLE_NAME}
        WHERE ${DbLikedGame.Schema.GAME_ID} = :gameId
        """,
    )
    suspend fun deleteLikedGame(gameId: Int)

    @Query(
        """
        SELECT COUNT(*) FROM ${DbLikedGame.Schema.TABLE_NAME}
        WHERE ${DbLikedGame.Schema.GAME_ID} = :gameId
        """,
    )
    suspend fun isGameLiked(gameId: Int): Boolean

    @Query(
        """
        SELECT COUNT(*) FROM ${DbLikedGame.Schema.TABLE_NAME}
        WHERE ${DbLikedGame.Schema.GAME_ID} = :gameId
        """,
    )
    fun observeGameLikeState(gameId: Int): Flow<Boolean>

    @Query(
        """
        SELECT g.* FROM ${DbGame.Schema.TABLE_NAME} AS g
        INNER JOIN ${DbLikedGame.Schema.TABLE_NAME} AS lg
        ON lg.${DbLikedGame.Schema.GAME_ID} = g.${DbGame.Schema.ID}
        ORDER BY lg.${DbLikedGame.Schema.LIKE_TIMESTAMP} DESC
        LIMIT :offset, :limit
        """,
    )
    fun observeLikedGames(offset: Int, limit: Int): Flow<List<DbGame>>
}
