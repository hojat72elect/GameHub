package ca.six.hojat.gamehub.shared.data.local.games.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalGame
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGames(games: List<LocalGame>)

    @Query(
        """
        SELECT * FROM ${LocalGame.Schema.TABLE_NAME}
        WHERE ${LocalGame.Schema.ID} = :id
        """,
    )
    suspend fun getGame(id: Int): LocalGame?

    @Query(
        """
        SELECT * FROM ${LocalGame.Schema.TABLE_NAME}
        WHERE ${LocalGame.Schema.ID} IN (:ids)
        LIMIT :offset, :limit
        """,
    )
    suspend fun getGames(ids: List<Int>, offset: Int, limit: Int): List<LocalGame>

    @Query(
        """
        SELECT * FROM ${LocalGame.Schema.TABLE_NAME}
        WHERE LOWER(${LocalGame.Schema.NAME}) LIKE (:searchQuery || '%')
        ORDER BY
            ${LocalGame.Schema.TOTAL_RATING} IS NULL,
            ${LocalGame.Schema.TOTAL_RATING} DESC,
            ${LocalGame.Schema.ID} ASC
        LIMIT :offset, :limit
        """,
    )
    suspend fun searchGames(searchQuery: String, offset: Int, limit: Int): List<LocalGame>

    @Query(
        """
        SELECT * FROM ${LocalGame.Schema.TABLE_NAME}
        WHERE ${LocalGame.Schema.USERS_RATING} IS NOT NULL AND
        ${LocalGame.Schema.RELEASE_DATE} IS NOT NULL AND
        ${LocalGame.Schema.RELEASE_DATE} > :minReleaseDateTimestamp
        ORDER BY ${LocalGame.Schema.TOTAL_RATING} DESC
        LIMIT :offset, :limit
        """,
    )
    fun observePopularGames(minReleaseDateTimestamp: Long, offset: Int, limit: Int): Flow<List<LocalGame>>

    @Query(
        """
        SELECT * FROM ${LocalGame.Schema.TABLE_NAME}
        WHERE ${LocalGame.Schema.RELEASE_DATE} IS NOT NULL AND
        ${LocalGame.Schema.RELEASE_DATE} > :minReleaseDateTimestamp AND
        ${LocalGame.Schema.RELEASE_DATE} < :maxReleaseDateTimestamp
        ORDER BY ${LocalGame.Schema.RELEASE_DATE} DESC
        LIMIT :offset, :limit
        """,
    )
    fun observeRecentlyReleasedGames(
        minReleaseDateTimestamp: Long,
        maxReleaseDateTimestamp: Long,
        offset: Int,
        limit: Int,
    ): Flow<List<LocalGame>>

    @Query(
        """
        SELECT * FROM ${LocalGame.Schema.TABLE_NAME}
        WHERE ${LocalGame.Schema.RELEASE_DATE} IS NOT NULL AND
        ${LocalGame.Schema.RELEASE_DATE} > :minReleaseDateTimestamp
        ORDER BY ${LocalGame.Schema.RELEASE_DATE} ASC
        LIMIT :offset, :limit
        """,
    )
    fun observeComingSoonGames(minReleaseDateTimestamp: Long, offset: Int, limit: Int): Flow<List<LocalGame>>

    @Query(
        """
        SELECT * FROM ${LocalGame.Schema.TABLE_NAME}
        WHERE ${LocalGame.Schema.RELEASE_DATE} IS NOT NULL AND
        ${LocalGame.Schema.RELEASE_DATE} > :minReleaseDateTimestamp AND
        ${LocalGame.Schema.HYPE_COUNT} IS NOT NULL
        ORDER BY ${LocalGame.Schema.HYPE_COUNT} DESC
        LIMIT :offset, :limit
        """,
    )
    fun observeMostAnticipatedGames(minReleaseDateTimestamp: Long, offset: Int, limit: Int): Flow<List<LocalGame>>
}
