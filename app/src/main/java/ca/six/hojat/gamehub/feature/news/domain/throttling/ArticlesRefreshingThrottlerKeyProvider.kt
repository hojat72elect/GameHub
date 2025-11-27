package ca.six.hojat.gamehub.feature.news.domain.throttling

import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import javax.inject.Inject

internal interface ArticlesRefreshingThrottlerKeyProvider {
    fun provideArticlesKey(pagination: Pagination): String
}

internal class ArticlesRefreshingThrottlerKeyProviderImpl @Inject constructor() :
    ArticlesRefreshingThrottlerKeyProvider {

    override fun provideArticlesKey(pagination: Pagination): String {
        return "articles | offset: ${pagination.offset} | limit: ${pagination.limit}"
    }
}
