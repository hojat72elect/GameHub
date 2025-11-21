
package com.paulrybitskyi.gamedge.feature.news.domain.throttling

import javax.inject.Inject

internal class ArticlesRefreshingThrottlerTools @Inject constructor(
    val throttler: ArticlesRefreshingThrottler,
    val keyProvider: ArticlesRefreshingThrottlerKeyProvider,
)
