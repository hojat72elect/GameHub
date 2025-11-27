package ca.six.hojat.gamehub.feature.info.domain.usecases.likes

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.usecases.ObservableUseCase
import ca.six.hojat.gamehub.common.domain.games.datastores.LikedGamesLocalDataStore
import ca.six.hojat.gamehub.feature.info.domain.usecases.likes.ObserveGameLikeStateUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

internal interface ObserveGameLikeStateUseCase : ObservableUseCase<Params, Boolean> {

    data class Params(val gameId: Int)
}

@Singleton
internal class ObserveGameLikeStateUseCaseImpl @Inject constructor(
    private val likedGamesLocalDataStore: LikedGamesLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
) : ObserveGameLikeStateUseCase {

    override fun execute(params: Params): Flow<Boolean> {
        return likedGamesLocalDataStore.observeGameLikeState(params.gameId)
            .flowOn(dispatcherProvider.main)
    }
}
