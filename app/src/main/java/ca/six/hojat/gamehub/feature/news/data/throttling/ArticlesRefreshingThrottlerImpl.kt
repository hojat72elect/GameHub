package ca.six.hojat.gamehub.feature.news.data.throttling

import ca.six.hojat.gamehub.core.providers.TimestampProvider
import ca.six.hojat.gamehub.feature.news.domain.throttling.ArticlesRefreshingThrottler
import ca.six.hojat.gamehub.shared.data.local.news_articles.entities.LocalNewsArticlesRefreshingTimestamp
import ca.six.hojat.gamehub.shared.data.local.news_articles.tables.NewsArticlesRefreshingTimestampsTable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class ArticlesRefreshingThrottlerImpl @Inject constructor(
    private val newsArticlesRefreshingTimestampsTable: NewsArticlesRefreshingTimestampsTable,
    private val timestampProvider: TimestampProvider,
) : ArticlesRefreshingThrottler {

    private companion object {
        private val ARTICLES_REFRESH_TIMEOUT = TimeUnit.MINUTES.toMillis(10L)
    }

    override suspend fun canRefreshArticles(key: String): Boolean {
        val lastRefreshTimestamp = newsArticlesRefreshingTimestampsTable.get(key)?.lastRefreshTimestamp ?: 0L
        return timestampProvider.getUnixTimestamp() > (lastRefreshTimestamp + ARTICLES_REFRESH_TIMEOUT)
    }

    override suspend fun updateArticlesLastRefreshTime(key: String) {
        newsArticlesRefreshingTimestampsTable.save(
            LocalNewsArticlesRefreshingTimestamp(
                key = key,
                lastRefreshTimestamp = timestampProvider.getUnixTimestamp(),
            )
        )
    }
}
