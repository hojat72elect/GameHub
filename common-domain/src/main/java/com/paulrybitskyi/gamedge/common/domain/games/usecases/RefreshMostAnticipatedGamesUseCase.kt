package com.paulrybitskyi.gamedge.common.domain.games.usecases

import com.paulrybitskyi.gamedge.common.domain.common.DispatcherProvider
import com.paulrybitskyi.gamedge.common.domain.common.DomainResult
import com.paulrybitskyi.gamedge.common.domain.common.extensions.onEachSuccess
import com.paulrybitskyi.gamedge.common.domain.games.RefreshableGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.common.RefreshGamesUseCaseParams
import com.paulrybitskyi.gamedge.common.domain.games.common.throttling.GamesRefreshingThrottlerTools
import com.paulrybitskyi.gamedge.common.domain.games.datastores.GamesDataStores
import com.paulrybitskyi.gamedge.common.domain.games.entities.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface RefreshMostAnticipatedGamesUseCase : RefreshableGamesUseCase

@Singleton
internal class RefreshMostAnticipatedGamesUseCaseImpl @Inject constructor(
    private val gamesDataStores: GamesDataStores,
    private val throttlerTools: GamesRefreshingThrottlerTools,
    private val dispatcherProvider: DispatcherProvider,
) : RefreshMostAnticipatedGamesUseCase {

    override fun execute(params: RefreshGamesUseCaseParams): Flow<DomainResult<List<Game>>> {
        val throttlerKey = throttlerTools.keyProvider.provideMostAnticipatedGamesKey(params.pagination)

        return flow {
            if (throttlerTools.throttler.canRefreshGames(throttlerKey)) {
                emit(gamesDataStores.remote.getMostAnticipatedGames(params.pagination))
            }
        }
            .onEachSuccess { games ->
                gamesDataStores.local.saveGames(games)
                throttlerTools.throttler.updateGamesLastRefreshTime(throttlerKey)
            }
            .flowOn(dispatcherProvider.main)
    }
}
