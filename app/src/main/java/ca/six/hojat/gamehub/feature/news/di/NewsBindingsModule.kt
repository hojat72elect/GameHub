package ca.six.hojat.gamehub.feature.news.di

import ca.six.hojat.gamehub.feature.news.data.datastores.database.ArticlesDatabaseDataStoreImpl
import ca.six.hojat.gamehub.feature.news.data.datastores.gamespot.ArticlesGamespotDataStoreImpl
import ca.six.hojat.gamehub.feature.news.data.throttling.ArticlesRefreshingThrottlerImpl
import ca.six.hojat.gamehub.feature.news.domain.datastores.ArticlesLocalDataStore
import ca.six.hojat.gamehub.feature.news.domain.datastores.ArticlesRemoteDataStore
import ca.six.hojat.gamehub.feature.news.domain.throttling.ArticlesRefreshingThrottler
import ca.six.hojat.gamehub.feature.news.domain.throttling.ArticlesRefreshingThrottlerKeyProvider
import ca.six.hojat.gamehub.feature.news.domain.throttling.ArticlesRefreshingThrottlerKeyProviderImpl
import ca.six.hojat.gamehub.feature.news.domain.usecases.ObserveArticlesUseCase
import ca.six.hojat.gamehub.feature.news.domain.usecases.ObserveArticlesUseCaseImpl
import ca.six.hojat.gamehub.feature.news.domain.usecases.RefreshArticlesUseCase
import ca.six.hojat.gamehub.feature.news.domain.usecases.RefreshArticlesUseCaseImpl
import ca.six.hojat.gamehub.feature.news.presentation.mapping.GamingNewsItemUiModelMapper
import ca.six.hojat.gamehub.feature.news.presentation.mapping.GamingNewsItemUiModelMapperImpl
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