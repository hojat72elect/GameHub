package ca.six.hojat.gamehub.shared.data.local.games.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.six.hojat.gamehub.shared.data.local.games.entities.DbGame
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGames(games: List<DbGame>)

    @Query(
        """
        SELECT * FROM ${DbGame.Schema.TABLE_NAME}
        WHERE ${DbGame.Schema.ID} = :id
        """,
    )
    suspend fun getGame(id: Int): DbGame?

    @Query(
        """
        SELECT * FROM ${DbGame.Schema.TABLE_NAME}
        WHERE ${DbGame.Schema.ID} IN (:ids)
        LIMIT :offset, :limit
        """,
    )
    suspend fun getGames(ids: List<Int>, offset: Int, limit: Int): List<DbGame>

    @Query(
        """
        SELECT * FROM ${DbGame.Schema.TABLE_NAME}
        WHERE LOWER(${DbGame.Schema.NAME}) LIKE (:searchQuery || '%')
        ORDER BY
            ${DbGame.Schema.TOTAL_RATING} IS NULL,
            ${DbGame.Schema.TOTAL_RATING} DESC,
            ${DbGame.Schema.ID} ASC
        LIMIT :offset, :limit
        """,
    )
    suspend fun searchGames(searchQuery: String, offset: Int, limit: Int): List<DbGame>

    @Query(
        """
        SELECT * FROM ${DbGame.Schema.TABLE_NAME}
        WHERE ${DbGame.Schema.USERS_RATING} IS NOT NULL AND
        ${DbGame.Schema.RELEASE_DATE} IS NOT NULL AND
        ${DbGame.Schema.RELEASE_DATE} > :minReleaseDateTimestamp
        ORDER BY ${DbGame.Schema.TOTAL_RATING} DESC
        LIMIT :offset, :limit
        """,
    )
    fun observePopularGames(minReleaseDateTimestamp: Long, offset: Int, limit: Int): Flow<List<DbGame>>

    @Query(
        """
        SELECT * FROM ${DbGame.Schema.TABLE_NAME}
        WHERE ${DbGame.Schema.RELEASE_DATE} IS NOT NULL AND
        ${DbGame.Schema.RELEASE_DATE} > :minReleaseDateTimestamp AND
        ${DbGame.Schema.RELEASE_DATE} < :maxReleaseDateTimestamp
        ORDER BY ${DbGame.Schema.RELEASE_DATE} DESC
        LIMIT :offset, :limit
        """,
    )
    fun observeRecentlyReleasedGames(
        minReleaseDateTimestamp: Long,
        maxReleaseDateTimestamp: Long,
        offset: Int,
        limit: Int,
    ): Flow<List<DbGame>>

    @Query(
        """
        SELECT * FROM ${DbGame.Schema.TABLE_NAME}
        WHERE ${DbGame.Schema.RELEASE_DATE} IS NOT NULL AND
        ${DbGame.Schema.RELEASE_DATE} > :minReleaseDateTimestamp
        ORDER BY ${DbGame.Schema.RELEASE_DATE} ASC
        LIMIT :offset, :limit
        """,
    )
    fun observeComingSoonGames(minReleaseDateTimestamp: Long, offset: Int, limit: Int): Flow<List<DbGame>>

    @Query(
        """
        SELECT * FROM ${DbGame.Schema.TABLE_NAME}
        WHERE ${DbGame.Schema.RELEASE_DATE} IS NOT NULL AND
        ${DbGame.Schema.RELEASE_DATE} > :minReleaseDateTimestamp AND
        ${DbGame.Schema.HYPE_COUNT} IS NOT NULL
        ORDER BY ${DbGame.Schema.HYPE_COUNT} DESC
        LIMIT :offset, :limit
        """,
    )
    fun observeMostAnticipatedGames(minReleaseDateTimestamp: Long, offset: Int, limit: Int): Flow<List<DbGame>>
}
