package ca.six.hojat.gamehub.feature.info.domain.usecases

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.DomainResult
import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import ca.six.hojat.gamehub.common.domain.common.extensions.onEachSuccess
import ca.six.hojat.gamehub.common.domain.common.usecases.UseCase
import ca.six.hojat.gamehub.common.domain.games.common.throttling.GamesRefreshingThrottlerTools
import ca.six.hojat.gamehub.common.domain.games.datastores.GamesDataStores
import ca.six.hojat.gamehub.common.domain.games.entities.Game
import ca.six.hojat.gamehub.feature.info.domain.usecases.RefreshSimilarGamesUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal interface RefreshSimilarGamesUseCase : UseCase<Params, Flow<DomainResult<List<Game>>>> {

    data class Params(
        val game: Game,
        val pagination: Pagination,
    )
}

internal class RefreshSimilarGamesUseCaseImpl @Inject constructor(
    private val gamesDataStores: GamesDataStores,
    private val dispatcherProvider: DispatcherProvider,
    private val throttlerTools: GamesRefreshingThrottlerTools,
) : RefreshSimilarGamesUseCase {

    override suspend fun execute(params: Params): Flow<DomainResult<List<Game>>> {
        val throttlerKey = withContext(dispatcherProvider.computation) {
            throttlerTools.keyProvider.provideSimilarGamesKey(
                params.game,
                params.pagination,
            )
        }

        return flow {
            if (throttlerTools.throttler.canRefreshSimilarGames(throttlerKey)) {
                emit(gamesDataStores.remote.getSimilarGames(params.game, params.pagination))
            }
        }
            .onEachSuccess { games ->
                gamesDataStores.local.saveGames(games)
                throttlerTools.throttler.updateGamesLastRefreshTime(throttlerKey)
            }
            .flowOn(dispatcherProvider.main)
    }
}
