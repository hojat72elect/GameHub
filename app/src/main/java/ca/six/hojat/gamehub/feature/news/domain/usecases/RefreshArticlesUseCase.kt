package ca.six.hojat.gamehub.feature.news.domain.usecases

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.DomainResult
import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import ca.six.hojat.gamehub.common.domain.common.extensions.onEachSuccess
import ca.six.hojat.gamehub.common.domain.common.usecases.ObservableUseCase
import ca.six.hojat.gamehub.feature.news.domain.datastores.ArticlesDataStores
import ca.six.hojat.gamehub.feature.news.domain.entities.Article
import ca.six.hojat.gamehub.feature.news.domain.throttling.ArticlesRefreshingThrottlerTools
import ca.six.hojat.gamehub.feature.news.domain.usecases.RefreshArticlesUseCase.Params
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal interface RefreshArticlesUseCase : ObservableUseCase<Params, DomainResult<List<Article>>> {

    data class Params(val pagination: Pagination = Pagination())
}

internal class RefreshArticlesUseCaseImpl @Inject constructor(
    private val articlesDataStores: ArticlesDataStores,
    private val dispatcherProvider: DispatcherProvider,
    private val throttlerTools: ArticlesRefreshingThrottlerTools,
) : RefreshArticlesUseCase {

    override fun execute(params: Params): Flow<DomainResult<List<Article>>> {
        val throttlerKey = throttlerTools.keyProvider.provideArticlesKey(params.pagination)

        return flow {
            if (throttlerTools.throttler.canRefreshArticles(throttlerKey)) {
                emit(articlesDataStores.remote.getArticles(params.pagination))
            }
        }
            .onEachSuccess { articles ->
                articlesDataStores.local.saveArticles(articles)
                throttlerTools.throttler.updateArticlesLastRefreshTime(throttlerKey)
            }
            .flowOn(dispatcherProvider.main)
    }
}
