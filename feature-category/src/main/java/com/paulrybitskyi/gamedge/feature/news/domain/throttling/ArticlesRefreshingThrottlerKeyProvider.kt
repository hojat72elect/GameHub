package com.paulrybitskyi.gamedge.feature.news.domain.throttling

import com.paulrybitskyi.gamedge.common.domain.common.entities.Pagination
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject
import javax.inject.Singleton

internal interface ArticlesRefreshingThrottlerKeyProvider {
    fun provideArticlesKey(pagination: Pagination): String
}

@Singleton
@BindType
internal class ArticlesRefreshingThrottlerKeyProviderImpl @Inject constructor() :
    ArticlesRefreshingThrottlerKeyProvider {

    override fun provideArticlesKey(pagination: Pagination): String {
        return "articles | offset: ${pagination.offset} | limit: ${pagination.limit}"
    }
}
