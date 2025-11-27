package ca.six.hojat.gamehub.feature.likes.domain

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.games.ObservableGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.common.ObserveGamesUseCaseParams
import ca.six.hojat.gamehub.common.domain.games.datastores.LikedGamesLocalDataStore
import ca.six.hojat.gamehub.common.domain.games.entities.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

internal interface ObserveLikedGamesUseCase : ObservableGamesUseCase

@Singleton
internal class ObserveLikedGamesUseCaseImpl @Inject constructor(
    private val likedGamesLocalDataStore: LikedGamesLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
) : ObserveLikedGamesUseCase {

    override fun execute(params: ObserveGamesUseCaseParams): Flow<List<Game>> {
        return likedGamesLocalDataStore.observeLikedGames(params.pagination)
            .flowOn(dispatcherProvider.main)
    }
}
