package com.paulrybitskyi.gamedge.feature.info.domain.usecases

import com.github.michaelbull.result.Ok
import com.paulrybitskyi.gamedge.common.domain.common.DispatcherProvider
import com.paulrybitskyi.gamedge.common.domain.common.DomainResult
import com.paulrybitskyi.gamedge.common.domain.common.entities.Pagination
import com.paulrybitskyi.gamedge.common.domain.common.usecases.UseCase
import com.paulrybitskyi.gamedge.common.domain.games.datastores.GamesLocalDataStore
import com.paulrybitskyi.gamedge.common.domain.games.entities.Company
import com.paulrybitskyi.gamedge.common.domain.games.entities.Game
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetCompanyDevelopedGamesUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject
import javax.inject.Singleton

internal interface GetCompanyDevelopedGamesUseCase : UseCase<Params, Flow<DomainResult<List<Game>>>> {

    data class Params(
        val company: Company,
        val pagination: Pagination,
    )
}

@Singleton
internal class GetCompanyDevelopedGamesUseCaseImpl @Inject constructor(
    private val refreshCompanyDevelopedGamesUseCase: RefreshCompanyDevelopedGamesUseCase,
    private val gamesLocalDataStore: GamesLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
) : GetCompanyDevelopedGamesUseCase {

    override suspend fun execute(params: Params): Flow<DomainResult<List<Game>>> {
        return refreshCompanyDevelopedGamesUseCase
            .execute(RefreshCompanyDevelopedGamesUseCase.Params(params.company, params.pagination))
            .onEmpty {
                val localCompanyDevelopedGamesFlow = flow {
                    emit(gamesLocalDataStore.getCompanyDevelopedGames(params.company, params.pagination))
                }
                    .map<List<Game>, DomainResult<List<Game>>>(::Ok)

                emitAll(localCompanyDevelopedGamesFlow)
            }
            .flowOn(dispatcherProvider.main)
    }
}
