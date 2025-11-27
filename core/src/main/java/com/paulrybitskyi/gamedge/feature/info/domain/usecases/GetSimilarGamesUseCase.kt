package com.paulrybitskyi.gamedge.feature.info.domain.usecases

import com.github.michaelbull.result.Ok
import com.paulrybitskyi.gamedge.common.domain.common.DispatcherProvider
import com.paulrybitskyi.gamedge.common.domain.common.DomainResult
import com.paulrybitskyi.gamedge.common.domain.common.entities.Pagination
import com.paulrybitskyi.gamedge.common.domain.common.usecases.UseCase
import com.paulrybitskyi.gamedge.common.domain.games.datastores.GamesLocalDataStore
import com.paulrybitskyi.gamedge.common.domain.games.entities.Game
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetSimilarGamesUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject
import javax.inject.Singleton

internal interface GetSimilarGamesUseCase : UseCase<Params, Flow<DomainResult<List<Game>>>> {

    data class Params(
        val game: Game,
        val pagination: Pagination,
    )
}

@Singleton
internal class GetSimilarGamesUseCaseImpl @Inject constructor(
    private val refreshSimilarGamesUseCase: RefreshSimilarGamesUseCase,
    private val gamesLocalDataStore: GamesLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
) : GetSimilarGamesUseCase {

    override suspend fun execute(params: Params): Flow<DomainResult<List<Game>>> {
        return refreshSimilarGamesUseCase
            .execute(RefreshSimilarGamesUseCase.Params(params.game, params.pagination))
            .onEmpty {
                val localSimilarGamesFlow = flow {
                    emit(gamesLocalDataStore.getSimilarGames(params.game, params.pagination))
                }
                    .map<List<Game>, DomainResult<List<Game>>>(::Ok)

                emitAll(localSimilarGamesFlow)
            }
            .flowOn(dispatcherProvider.main)
    }
}
