package ca.six.hojat.gamehub.feature.news.data.datastores.database

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import ca.six.hojat.gamehub.feature.news.domain.datastores.ArticlesLocalDataStore
import ca.six.hojat.gamehub.feature.news.domain.entities.Article
import ca.six.hojat.gamehub.shared.data.local.news_articles.entities.LocalNewsArticle
import ca.six.hojat.gamehub.shared.data.local.news_articles.tables.NewsArticlesTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ArticlesDatabaseDataStoreImpl @Inject constructor(
    private val newsArticlesTable: NewsArticlesTable,
    private val dispatcherProvider: DispatcherProvider,
    private val dbArticleMapper: DbArticleMapper,
) : ArticlesLocalDataStore {

    override suspend fun saveArticles(articles: List<Article>) {
        newsArticlesTable.saveArticles(
            withContext(dispatcherProvider.computation) {
                dbArticleMapper.mapToDatabaseArticles(articles)
            },
        )
    }

    override fun observeArticles(pagination: Pagination): Flow<List<Article>> {
        return newsArticlesTable.observeArticles(
            offset = pagination.offset,
            limit = pagination.limit,
        )
            .toDataArticlesFlow()
    }

    private fun Flow<List<LocalNewsArticle>>.toDataArticlesFlow(): Flow<List<Article>> {
        return distinctUntilChanged()
            .map(dbArticleMapper::mapToDomainArticles)
            .flowOn(dispatcherProvider.computation)
    }
}
