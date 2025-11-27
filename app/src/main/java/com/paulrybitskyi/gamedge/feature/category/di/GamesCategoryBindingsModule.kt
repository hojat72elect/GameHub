package com.paulrybitskyi.gamedge.feature.category.di

import com.paulrybitskyi.gamedge.feature.category.widgets.GameCategoryUiModelMapper
import com.paulrybitskyi.gamedge.feature.category.widgets.GameCategoryUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.discovery.mapping.GamesDiscoveryItemGameUiModelMapper
import com.paulrybitskyi.gamedge.feature.discovery.mapping.GamesDiscoveryItemGameUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetGameImageUrlsUseCase
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetGameImageUrlsUseCaseImpl
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetGameInfoUseCase
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetGameInfoUseCaseImpl
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.RefreshCompanyDevelopedGamesUseCase
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.RefreshCompanyDevelopedGamesUseCaseImpl
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.RefreshSimilarGamesUseCase
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.RefreshSimilarGamesUseCaseImpl
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.likes.ToggleGameLikeStateUseCase
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.likes.ToggleGameLikeStateUseCaseImpl
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.companies.GameInfoCompanyUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.companies.GameInfoCompanyUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.details.GameInfoDetailsUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.details.GameInfoDetailsUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header.GameInfoHeaderUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header.GameInfoHeaderUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header.artworks.GameInfoArtworkUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header.artworks.GameInfoArtworkUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.links.GameInfoLinkUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.links.GameInfoLinkUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.main.GameInfoUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.main.GameInfoUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames.mappers.GameInfoOtherCompanyGamesUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames.mappers.GameInfoOtherCompanyGamesUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames.mappers.GameInfoSimilarGamesUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.relatedgames.mappers.GameInfoSimilarGamesUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.screenshots.GameInfoScreenshotUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.screenshots.GameInfoScreenshotUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.videos.GameInfoVideoUiModelMapper
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.videos.GameInfoVideoUiModelMapperImpl
import com.paulrybitskyi.gamedge.feature.search.domain.SearchGamesUseCase
import com.paulrybitskyi.gamedge.feature.search.domain.SearchGamesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface GamesCategoryBindingsModule {

    @Binds
    @Singleton
    fun bindGetGameImageUrlsUseCase(binding: GetGameImageUrlsUseCaseImpl): GetGameImageUrlsUseCase

    @Binds
    @Singleton
    fun bindGetGameInfoUseCase(binding: GetGameInfoUseCaseImpl): GetGameInfoUseCase

    @Binds
    @Singleton
    fun bindToggleGameLikeStateUseCase(binding: ToggleGameLikeStateUseCaseImpl): ToggleGameLikeStateUseCase

    @Binds
    @Singleton
    fun bindRefreshCompanyDevelopedGamesUseCase(binding: RefreshCompanyDevelopedGamesUseCaseImpl): RefreshCompanyDevelopedGamesUseCase

    @Binds
    @Singleton
    fun bindRefreshSimilarGamesUseCase(binding: RefreshSimilarGamesUseCaseImpl): RefreshSimilarGamesUseCase

    @Binds
    @Singleton
    fun bindSearchGamesUseCase(binding: SearchGamesUseCaseImpl): SearchGamesUseCase
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface GamesCategoryViewModelBindingsModule {

    @Binds
    fun bindGameCategoryUiModelMapper(binding: GameCategoryUiModelMapperImpl): GameCategoryUiModelMapper

    @Binds
    fun bindGamesDiscoveryItemGameUiModelMapper(binding: GamesDiscoveryItemGameUiModelMapperImpl): GamesDiscoveryItemGameUiModelMapper

    @Binds
    fun bindGameInfoCompanyUiModelMapper(binding: GameInfoCompanyUiModelMapperImpl): GameInfoCompanyUiModelMapper

    @Binds
    fun bindGameInfoDetailsUiModelMapper(binding: GameInfoDetailsUiModelMapperImpl): GameInfoDetailsUiModelMapper

    @Binds
    fun bindGameInfoHeaderUiModelMapper(binding: GameInfoHeaderUiModelMapperImpl): GameInfoHeaderUiModelMapper

    @Binds
    fun bindGameInfoArtworkUiModelMapper(binding: GameInfoArtworkUiModelMapperImpl): GameInfoArtworkUiModelMapper

    @Binds
    fun bindGameInfoLinkUiModelMapper(binding: GameInfoLinkUiModelMapperImpl): GameInfoLinkUiModelMapper

    @Binds
    fun bindGameInfoUiModelMapper(binding: GameInfoUiModelMapperImpl): GameInfoUiModelMapper

    @Binds
    fun bindGameInfoOtherCompanyGamesUiModelMapper(binding: GameInfoOtherCompanyGamesUiModelMapperImpl): GameInfoOtherCompanyGamesUiModelMapper

    @Binds
    fun bindGameInfoSimilarGamesUiModelMapper(binding: GameInfoSimilarGamesUiModelMapperImpl): GameInfoSimilarGamesUiModelMapper

    @Binds
    fun bindGameInfoScreenshotUiModelMapper(binding: GameInfoScreenshotUiModelMapperImpl): GameInfoScreenshotUiModelMapper

    @Binds
    fun bindGameInfoVideoUiModelMapper(binding: GameInfoVideoUiModelMapperImpl): GameInfoVideoUiModelMapper
}