package ca.six.hojat.gamehub.common.domain.games.usecases

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.DomainResult
import ca.six.hojat.gamehub.common.domain.common.extensions.onEachSuccess
import ca.six.hojat.gamehub.common.domain.games.RefreshableGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.common.RefreshGamesUseCaseParams
import ca.six.hojat.gamehub.common.domain.games.common.throttling.GamesRefreshingThrottlerTools
import ca.six.hojat.gamehub.common.domain.games.datastores.GamesDataStores
import ca.six.hojat.gamehub.common.domain.games.entities.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface RefreshComingSoonGamesUseCase : RefreshableGamesUseCase

@Singleton
internal class RefreshComingSoonGamesUseCaseImpl @Inject constructor(
    private val gamesDataStores: GamesDataStores,
    private val throttlerTools: GamesRefreshingThrottlerTools,
    private val dispatcherProvider: DispatcherProvider,
) : RefreshComingSoonGamesUseCase {

    override fun execute(params: RefreshGamesUseCaseParams): Flow<DomainResult<List<Game>>> {
        val throttlerKey = throttlerTools.keyProvider.provideComingSoonGamesKey(params.pagination)

        return flow {
            if (throttlerTools.throttler.canRefreshGames(throttlerKey)) {
                emit(gamesDataStores.remote.getComingSoonGames(params.pagination))
            }
        }
            .onEachSuccess { games ->
                gamesDataStores.local.saveGames(games)
                throttlerTools.throttler.updateGamesLastRefreshTime(throttlerKey)
            }
            .flowOn(dispatcherProvider.main)
    }
}
