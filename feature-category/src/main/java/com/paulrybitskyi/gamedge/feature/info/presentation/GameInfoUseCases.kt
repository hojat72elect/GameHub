package com.paulrybitskyi.gamedge.feature.info.presentation

import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetGameImageUrlsUseCase
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetGameInfoUseCase
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.likes.ToggleGameLikeStateUseCase
import javax.inject.Inject

internal class GameInfoUseCases @Inject constructor(
    val getGameInfoUseCase: GetGameInfoUseCase,
    val getGameImageUrlsUseCase: GetGameImageUrlsUseCase,
    val toggleGameLikeStateUseCase: ToggleGameLikeStateUseCase,
)
