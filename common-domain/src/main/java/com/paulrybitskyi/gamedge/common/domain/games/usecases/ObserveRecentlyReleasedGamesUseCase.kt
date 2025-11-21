package com.paulrybitskyi.gamedge.common.domain.games.usecases

import com.paulrybitskyi.gamedge.common.domain.common.DispatcherProvider
import com.paulrybitskyi.gamedge.common.domain.games.ObservableGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.common.ObserveGamesUseCaseParams
import com.paulrybitskyi.gamedge.common.domain.games.datastores.GamesLocalDataStore
import com.paulrybitskyi.gamedge.common.domain.games.entities.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface ObserveRecentlyReleasedGamesUseCase : ObservableGamesUseCase

@Singleton
internal class ObserveRecentlyReleasedGamesUseCaseImpl @Inject constructor(
    private val gamesLocalDataStore: GamesLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
) : ObserveRecentlyReleasedGamesUseCase {

    override fun execute(params: ObserveGamesUseCaseParams): Flow<List<Game>> {
        return gamesLocalDataStore.observeRecentlyReleasedGames(params.pagination)
            .flowOn(dispatcherProvider.main)
    }
}
