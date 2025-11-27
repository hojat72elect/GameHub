package ca.six.hojat.gamehub.feature.info.presentation

import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameImageUrlsUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameInfoUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.likes.ToggleGameLikeStateUseCase
import javax.inject.Inject

internal class GameInfoUseCases @Inject constructor(
    val getGameInfoUseCase: GetGameInfoUseCase,
    val getGameImageUrlsUseCase: GetGameImageUrlsUseCase,
    val toggleGameLikeStateUseCase: ToggleGameLikeStateUseCase,
)
