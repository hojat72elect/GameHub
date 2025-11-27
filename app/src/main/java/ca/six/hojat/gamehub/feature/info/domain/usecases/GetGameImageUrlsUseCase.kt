package ca.six.hojat.gamehub.feature.info.domain.usecases

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.DomainResult
import ca.six.hojat.gamehub.common.domain.common.entities.Error
import ca.six.hojat.gamehub.common.domain.common.extensions.mapSuccess
import ca.six.hojat.gamehub.common.domain.common.usecases.UseCase
import ca.six.hojat.gamehub.core.factories.ImageViewerGameUrlFactory
import ca.six.hojat.gamehub.core.utils.onError
import ca.six.hojat.gamehub.feature.info.domain.entities.GameImageType
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameImageUrlsUseCase.Params
import com.github.michaelbull.result.Err
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
