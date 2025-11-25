package com.paulrybitskyi.gamedge.common.data.games.common

import com.paulrybitskyi.gamedge.core.providers.TimestampProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private val POPULAR_GAMES_MIN_RELEASE_DATE_DURATION = TimeUnit.DAYS.toSeconds(@Suppress("MagicNumber") 90L)
private val RECENTLY_RELEASED_GAMES_MIN_RELEASE_DATE_DURATION = TimeUnit.DAYS.toSeconds(@Suppress("MagicNumber") 30L)

internal interface DiscoveryGamesReleaseDatesProvider {
    fun getPopularGamesMinReleaseDate(): Long
    fun getRecentlyReleasedGamesMinReleaseDate(): Long
    fun getRecentlyReleasedGamesMaxReleaseDate(): Long
    fun getComingSoonGamesMinReleaseDate(): Long
    fun getMostAnticipatedGamesMinReleaseDate(): Long
}

@Singleton
internal class DiscoveryGamesReleaseDatesProviderImpl @Inject constructor(
    private val timestampProvider: TimestampProvider,
) : DiscoveryGamesReleaseDatesProvider {

    override fun getPopularGamesMinReleaseDate(): Long {
        val currentUnixTimestamp = getUnixTimestamp()
        val minReleaseDateTimestamp = (currentUnixTimestamp - POPULAR_GAMES_MIN_RELEASE_DATE_DURATION)

        return minReleaseDateTimestamp
    }

    override fun getRecentlyReleasedGamesMinReleaseDate(): Long {
        val maxReleaseDateTimestamp = getRecentlyReleasedGamesMaxReleaseDate()
        val minReleaseDateTimestamp = (maxReleaseDateTimestamp - RECENTLY_RELEASED_GAMES_MIN_RELEASE_DATE_DURATION)

        return minReleaseDateTimestamp
    }

    override fun getRecentlyReleasedGamesMaxReleaseDate(): Long {
        return getUnixTimestamp()
    }

    override fun getComingSoonGamesMinReleaseDate(): Long {
        return getUnixTimestamp()
    }

    override fun getMostAnticipatedGamesMinReleaseDate(): Long {
        return getUnixTimestamp()
    }

    private fun getUnixTimestamp(): Long {
        return timestampProvider.getUnixTimestamp(TimeUnit.SECONDS)
    }
}
