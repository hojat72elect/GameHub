package ca.six.hojat.gamehub.common.domain.games.usecases

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.games.ObservableGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.common.ObserveGamesUseCaseParams
import ca.six.hojat.gamehub.common.domain.games.datastores.GamesLocalDataStore
import ca.six.hojat.gamehub.common.domain.games.entities.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface ObserveMostAnticipatedGamesUseCase : ObservableGamesUseCase

@Singleton
internal class ObserveMostAnticipatedGamesUseCaseImpl @Inject constructor(
    private val gamesLocalDataStore: GamesLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
) : ObserveMostAnticipatedGamesUseCase {

    override fun execute(params: ObserveGamesUseCaseParams): Flow<List<Game>> {
        return gamesLocalDataStore.observeMostAnticipatedGames(params.pagination)
            .flowOn(dispatcherProvider.main)
    }
}
