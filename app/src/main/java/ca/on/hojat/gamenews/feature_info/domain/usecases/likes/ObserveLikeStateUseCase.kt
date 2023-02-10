package ca.on.hojat.gamenews.feature_info.domain.usecases.likes

import ca.on.hojat.gamenews.core.domain.common.DispatcherProvider
import ca.on.hojat.gamenews.core.domain.common.usecases.ObservableUseCase
import ca.on.hojat.gamenews.core.domain.games.repository.LikedGamesLocalDataSource
import ca.on.hojat.gamenews.feature_info.domain.usecases.likes.ObserveLikeStateUseCase.Params
import com.paulrybitskyi.hiltbinder.BindType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

internal interface ObserveLikeStateUseCase : ObservableUseCase<Params, Boolean> {
    data class Params(val id: Int)
}

@Singleton
@BindType
internal class ObserveLikeStateUseCaseImpl @Inject constructor(
    private val likedGamesLocalDataSource: LikedGamesLocalDataSource,
    private val dispatcherProvider: DispatcherProvider,
) : ObserveLikeStateUseCase {

    override fun execute(params: Params): Flow<Boolean> {
        return likedGamesLocalDataSource.observeGameLikeState(params.id)
            .flowOn(dispatcherProvider.main)
    }
}