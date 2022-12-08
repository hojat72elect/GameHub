package ca.on.hojat.gamenews.shared.domain.games.usecases

import ca.on.hojat.gamenews.shared.domain.common.DispatcherProvider
import ca.on.hojat.gamenews.shared.domain.common.DomainResult
import ca.on.hojat.gamenews.shared.domain.common.extensions.onEachSuccess
import ca.on.hojat.gamenews.shared.domain.games.RefreshableGamesUseCase
import ca.on.hojat.gamenews.shared.domain.games.common.RefreshGamesUseCaseParams
import ca.on.hojat.gamenews.shared.domain.games.common.throttling.GamesRefreshingThrottlerTools
import ca.on.hojat.gamenews.shared.domain.games.datastores.GamesDataStores
import ca.on.hojat.gamenews.shared.domain.games.entities.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface RefreshRecentlyReleasedGamesUseCase : RefreshableGamesUseCase

@Singleton
internal class RefreshRecentlyReleasedGamesUseCaseImpl @Inject constructor(
    private val gamesDataStores: GamesDataStores,
    private val throttlerTools: GamesRefreshingThrottlerTools,
    private val dispatcherProvider: DispatcherProvider,
) : RefreshRecentlyReleasedGamesUseCase {

    override fun execute(params: RefreshGamesUseCaseParams): Flow<DomainResult<List<Game>>> {
        val throttlerKey =
            throttlerTools.keyProvider.provideRecentlyReleasedGamesKey(params.pagination)

        return flow {
            if (throttlerTools.throttler.canRefreshGames(throttlerKey)) {
                emit(gamesDataStores.remote.getRecentlyReleasedGames(params.pagination))
            }
        }
            .onEachSuccess { games ->
                gamesDataStores.local.saveGames(games)
                throttlerTools.throttler.updateGamesLastRefreshTime(throttlerKey)
            }
            .flowOn(dispatcherProvider.main)
    }
}