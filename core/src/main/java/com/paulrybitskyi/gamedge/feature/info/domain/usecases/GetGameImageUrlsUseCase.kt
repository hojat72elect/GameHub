package com.paulrybitskyi.gamedge.feature.info.domain.usecases

import com.github.michaelbull.result.Err
import com.paulrybitskyi.gamedge.common.domain.common.DispatcherProvider
import com.paulrybitskyi.gamedge.common.domain.common.DomainResult
import com.paulrybitskyi.gamedge.common.domain.common.entities.Error
import com.paulrybitskyi.gamedge.common.domain.common.extensions.mapSuccess
import com.paulrybitskyi.gamedge.common.domain.common.usecases.UseCase
import com.paulrybitskyi.gamedge.core.factories.ImageViewerGameUrlFactory
import com.paulrybitskyi.gamedge.core.utils.onError
import com.paulrybitskyi.gamedge.feature.info.domain.entities.GameImageType
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetGameImageUrlsUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal interface GetGameImageUrlsUseCase : UseCase<Params, Flow<DomainResult<List<String>>>> {

    data class Params(
        val gameId: Int,
        val imageType: GameImageType,
    )
}

internal class GetGameImageUrlsUseCaseImpl @Inject constructor(
    private val getGameUseCase: GetGameUseCase,
    private val gameUrlFactory: ImageViewerGameUrlFactory,
    private val dispatcherProvider: DispatcherProvider,
) : GetGameImageUrlsUseCase {

    override suspend fun execute(params: Params): Flow<DomainResult<List<String>>> {
        return getGameUseCase.execute(GetGameUseCase.Params(params.gameId))
            .flowOn(dispatcherProvider.main)
            .mapSuccess { game ->
                when (params.imageType) {
                    GameImageType.COVER -> {
                        gameUrlFactory.createCoverImageUrl(game)
                            ?.let(::listOf)
                            ?: error("Cannot create a game cover image url.")
                    }

                    GameImageType.ARTWORK -> gameUrlFactory.createArtworkImageUrls(game)
                    GameImageType.SCREENSHOT -> gameUrlFactory.createScreenshotImageUrls(game)
                }
            }
            .onError { error -> emit(Err(Error.Unknown(error.message.orEmpty()))) }
            .flowOn(dispatcherProvider.computation)
    }
}
