package com.paulrybitskyi.gamedge.feature.news.di

import com.paulrybitskyi.gamedge.feature.news.data.datastores.database.ArticlesDatabaseDataStoreImpl
import com.paulrybitskyi.gamedge.feature.news.data.datastores.gamespot.ArticlesGamespotDataStoreImpl
import com.paulrybitskyi.gamedge.feature.news.data.throttling.ArticlesRefreshingThrottlerImpl
import com.paulrybitskyi.gamedge.feature.news.domain.datastores.ArticlesLocalDataStore
import com.paulrybitskyi.gamedge.feature.news.domain.datastores.ArticlesRemoteDataStore
import com.paulrybitskyi.gamedge.feature.news.domain.throttling.ArticlesRefreshingThrottler
import com.paulrybitskyi.gamedge.feature.news.domain.throttling.ArticlesRefreshingThrottlerKeyProvider
import com.paulrybitskyi.gamedge.feature.news.domain.throttling.ArticlesRefreshingThrottlerKeyProviderImpl
import com.paulrybitskyi.gamedge.feature.news.domain.usecases.ObserveArticlesUseCase
import com.paulrybitskyi.gamedge.feature.news.domain.usecases.ObserveArticlesUseCaseImpl
import com.paulrybitskyi.gamedge.feature.news.domain.usecases.RefreshArticlesUseCase
import com.paulrybitskyi.gamedge.feature.news.domain.usecases.RefreshArticlesUseCaseImpl
import com.paulrybitskyi.gamedge.feature.news.presentation.mapping.GamingNewsItemUiModelMapper
import com.paulrybitskyi.gamedge.feature.news.presentation.mapping.GamingNewsItemUiModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NewsBindingsModule {

    @Binds
    @Singleton
    fun bindArticlesDatabaseDataStore(binding: ArticlesDatabaseDataStoreImpl): ArticlesLocalDataStore

    @Binds
    @Singleton
    fun bindArticlesGamespotDataStore(binding: ArticlesGamespotDataStoreImpl): ArticlesRemoteDataStore

    @Binds
    @Singleton
    fun bindArticlesRefreshingThrottler(binding: ArticlesRefreshingThrottlerImpl): ArticlesRefreshingThrottler

    @Binds
    @Singleton
    fun bindArticlesRefreshingThrottlerKeyProvider(binding: ArticlesRefreshingThrottlerKeyProviderImpl): ArticlesRefreshingThrottlerKeyProvider

    @Binds
    @Singleton
    fun bindObserveArticlesUseCase(binding: ObserveArticlesUseCaseImpl): ObserveArticlesUseCase

    @Binds
    @Singleton
    fun bindRefreshArticlesUseCase(binding: RefreshArticlesUseCaseImpl): RefreshArticlesUseCase
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface NewsViewModelBindingsModule {

    @Binds
    fun bindGamingNewsItemUiModelMapper(binding: GamingNewsItemUiModelMapperImpl): GamingNewsItemUiModelMapper
}