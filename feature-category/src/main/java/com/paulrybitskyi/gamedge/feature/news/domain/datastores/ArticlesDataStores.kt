package com.paulrybitskyi.gamedge.feature.news.domain.datastores

import javax.inject.Inject

internal class ArticlesDataStores @Inject constructor(
    val local: ArticlesLocalDataStore,
    val remote: ArticlesRemoteDataStore,
)
