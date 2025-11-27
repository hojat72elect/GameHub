package ca.six.hojat.gamehub.feature.search.domain

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.DomainResult
import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import ca.six.hojat.gamehub.common.domain.common.extensions.asSuccess
import ca.six.hojat.gamehub.common.domain.common.extensions.onSuccess
import ca.six.hojat.gamehub.common.domain.common.usecases.UseCase
import ca.six.hojat.gamehub.common.domain.games.datastores.GamesDataStores
import ca.six.hojat.gamehub.common.domain.games.entities.Game
import ca.six.hojat.gamehub.core.providers.NetworkStateProvider
import ca.six.hojat.gamehub.feature.search.domain.SearchGamesUseCase.Params
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal interface SearchGamesUseCase : UseCase<Params, Flow<DomainResult<List<Game>>>> {

    data class Params(
        val searchQuery: String,
        val pagination: Pagination = Pagination(),
    )
}

internal class SearchGamesUseCaseImpl @Inject constructor(
    private val gamesDataStores: GamesDataStores,
    private val dispatcherProvider: DispatcherProvider,
    private val networkStateProvider: NetworkStateProvider,
) : SearchGamesUseCase {

    private companion object {
        private const val LOCAL_SEARCH_DELAY_TIMEOUT = 150L
    }

    override suspend fun execute(params: Params): Flow<DomainResult<List<Game>>> {
        return flow { emit(searchGames(params)) }
            .flowOn(dispatcherProvider.main)
    }

    private suspend fun searchGames(params: Params): DomainResult<List<Game>> {
        val searchQuery = params.searchQuery
        val pagination = params.pagination

        if (networkStateProvider.isNetworkAvailable) {
            return gamesDataStores.remote
                .searchGames(searchQuery, pagination)
                .onSuccess(gamesDataStores.local::saveGames)
        }

        return gamesDataStores.local
            .searchGames(searchQuery, pagination)
            .asSuccess()
            // Delaying to give a sense of "loading" since it's really fast without it
            .also { delay(LOCAL_SEARCH_DELAY_TIMEOUT) }
    }
}
