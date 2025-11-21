package com.paulrybitskyi.gamedge.common.testing.domain

import com.paulrybitskyi.gamedge.common.domain.common.entities.Pagination
import com.paulrybitskyi.gamedge.common.domain.games.common.throttling.GamesRefreshingThrottlerKeyProvider
import com.paulrybitskyi.gamedge.common.domain.games.entities.Company
import com.paulrybitskyi.gamedge.common.domain.games.entities.Game

class FakeGamesRefreshingThrottlerKeyProvider : GamesRefreshingThrottlerKeyProvider {

    override fun providePopularGamesKey(pagination: Pagination): String {
        return "providePopularGamesKey"
    }

    override fun provideRecentlyReleasedGamesKey(pagination: Pagination): String {
        return "provideRecentlyReleasedGamesKey"
    }

    override fun provideComingSoonGamesKey(pagination: Pagination): String {
        return "provideComingSoonGamesKey"
    }

    override fun provideMostAnticipatedGamesKey(pagination: Pagination): String {
        return "provideMostAnticipatedGamesKey"
    }

    override fun provideCompanyDevelopedGamesKey(
        company: Company,
        pagination: Pagination,
    ): String {
        return "provideCompanyDevelopedGamesKey"
    }

    override fun provideSimilarGamesKey(game: Game, pagination: Pagination): String {
        return "provideSimilarGamesKey"
    }
}
