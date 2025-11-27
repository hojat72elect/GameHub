package ca.six.hojat.gamehub.feature.info.domain.usecases

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.DomainResult
import ca.six.hojat.gamehub.common.domain.common.entities.Error
import ca.six.hojat.gamehub.common.domain.common.usecases.UseCase
import ca.six.hojat.gamehub.common.domain.games.datastores.GamesLocalDataStore
import ca.six.hojat.gamehub.common.domain.games.entities.Game
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameUseCase.Params
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject
import javax.inject.Singleton

internal interface GetGameUseCase : UseCase<Params, Flow<DomainResult<Game>>> {

    data class Params(val gameId: Int)
}

@Singleton
internal class GetGameUseCaseImpl @Inject constructor(
    private val gamesLocalDataStore: GamesLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
) : GetGameUseCase {

    override suspend fun execute(params: Params): Flow<DomainResult<Game>> {
        return flow { gamesLocalDataStore.getGame(params.gameId)?.let { emit(it) } }
            .map<Game, DomainResult<Game>>(::Ok)
            .onEmpty { emit(Err(Error.NotFound("Could not find the game with ID = ${params.gameId}"))) }
            .flowOn(dispatcherProvider.main)
    }
}
