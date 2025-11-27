package ca.six.hojat.gamehub.feature.info.domain.usecases.likes

import ca.six.hojat.gamehub.common.domain.common.usecases.UseCase
import ca.six.hojat.gamehub.common.domain.games.datastores.LikedGamesLocalDataStore
import ca.six.hojat.gamehub.feature.info.domain.usecases.likes.ToggleGameLikeStateUseCase.Params
import javax.inject.Inject

internal interface ToggleGameLikeStateUseCase : UseCase<Params, Unit> {

    data class Params(val gameId: Int)
}

internal class ToggleGameLikeStateUseCaseImpl @Inject constructor(
    private val likedGamesLocalDataStore: LikedGamesLocalDataStore,
) : ToggleGameLikeStateUseCase {

    override suspend fun execute(params: Params) {
        if (likedGamesLocalDataStore.isGameLiked(params.gameId)) {
            likedGamesLocalDataStore.unlikeGame(params.gameId)
        } else {
            likedGamesLocalDataStore.likeGame(params.gameId)
        }
    }
}
