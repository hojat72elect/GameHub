package ca.six.hojat.gamehub.common.data.games.common.throttling

import ca.six.hojat.gamehub.common.domain.games.common.throttling.GamesRefreshingThrottler
import ca.six.hojat.gamehub.core.providers.TimestampProvider
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalGamesRefreshingTimestamp
import ca.six.hojat.gamehub.shared.data.local.games.tables.GamesRefreshingTimestampsTable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GamesRefreshingThrottlerImpl @Inject constructor(
    private val gamesRefreshingTimestampsTable: GamesRefreshingTimestampsTable,
    private val timestampProvider: TimestampProvider,
) : GamesRefreshingThrottler {

    private companion object {
        val DEFAULT_GAMES_REFRESH_TIMEOUT = TimeUnit.MINUTES.toMillis(10L)
        val COMPANY_DEVELOPED_GAMES_REFRESH_TIMEOUT = TimeUnit.DAYS.toMillis(7L)
        val SIMILAR_GAMES_REFRESH_TIMEOUT = TimeUnit.DAYS.toMillis(7L)
    }

    override suspend fun canRefreshGames(key: String): Boolean {
        return canRefreshGames(
            key = key,
            refreshTimeout = DEFAULT_GAMES_REFRESH_TIMEOUT,
        )
    }

    override suspend fun updateGamesLastRefreshTime(key: String) {
        gamesRefreshingTimestampsTable.save(
            LocalGamesRefreshingTimestamp(
                key = key,
                lastRefreshTimestamp = timestampProvider.getUnixTimestamp(),
            )
        )
    }

    override suspend fun canRefreshCompanyDevelopedGames(key: String): Boolean {
        return canRefreshGames(
            key = key,
            refreshTimeout = COMPANY_DEVELOPED_GAMES_REFRESH_TIMEOUT,
        )
    }

    override suspend fun canRefreshSimilarGames(key: String): Boolean {
        return canRefreshGames(
            key = key,
            refreshTimeout = SIMILAR_GAMES_REFRESH_TIMEOUT,
        )
    }

    private suspend fun canRefreshGames(key: String, refreshTimeout: Long): Boolean {
        val lastRefreshTimestamp = gamesRefreshingTimestampsTable.get(key)?.lastRefreshTimestamp ?: 0L
        return timestampProvider.getUnixTimestamp() > (lastRefreshTimestamp + refreshTimeout)
    }
}
