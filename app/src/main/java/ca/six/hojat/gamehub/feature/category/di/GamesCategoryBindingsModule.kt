package ca.six.hojat.gamehub.feature.category.di

import ca.six.hojat.gamehub.feature.category.widgets.GameCategoryUiModelMapper
import ca.six.hojat.gamehub.feature.category.widgets.GameCategoryUiModelMapperImpl
import ca.six.hojat.gamehub.feature.discovery.mapping.GamesDiscoveryItemGameUiModelMapper
import ca.six.hojat.gamehub.feature.discovery.mapping.GamesDiscoveryItemGameUiModelMapperImpl
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameImageUrlsUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameImageUrlsUseCaseImpl
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameInfoUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameInfoUseCaseImpl
import ca.six.hojat.gamehub.feature.info.domain.usecases.RefreshCompanyDevelopedGamesUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.RefreshCompanyDevelopedGamesUseCaseImpl
import ca.six.hojat.gamehub.feature.info.domain.usecases.RefreshSimilarGamesUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.RefreshSimilarGamesUseCaseImpl
import ca.six.hojat.gamehub.feature.info.domain.usecases.likes.ToggleGameLikeStateUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.likes.ToggleGameLikeStateUseCaseImpl
import ca.six.hojat.gamehub.feature.info.presentation.widgets.companies.GameInfoCompanyUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.companies.GameInfoCompanyUiModelMapperImpl
import ca.six.hojat.gamehub.feature.info.presentation.widgets.details.GameInfoDetailsUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.details.GameInfoDetailsUiModelMapperImpl
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.GameInfoHeaderUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.GameInfoHeaderUiModelMapperImpl
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.artworks.GameInfoArtworkUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.artworks.GameInfoArtworkUiModelMapperImpl
import ca.six.hojat.gamehub.feature.info.presentation.widgets.links.GameInfoLinkUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.links.GameInfoLinkUiModelMapperImpl
import ca.six.hojat.gamehub.feature.info.presentation.widgets.main.GameInfoUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.main.GameInfoUiModelMapperImpl
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.mappers.GameInfoOtherCompanyGamesUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.mappers.GameInfoOtherCompanyGamesUiModelMapperImpl
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.mappers.GameInfoSimilarGamesUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.mappers.GameInfoSimilarGamesUiModelMapperImpl
import ca.six.hojat.gamehub.feature.info.presentation.widgets.screenshots.GameInfoScreenshotUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.screenshots.GameInfoScreenshotUiModelMapperImpl
import ca.six.hojat.gamehub.feature.info.presentation.widgets.videos.GameInfoVideoUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.videos.GameInfoVideoUiModelMapperImpl
import ca.six.hojat.gamehub.feature.search.domain.SearchGamesUseCase
import ca.six.hojat.gamehub.feature.search.domain.SearchGamesUseCaseImpl
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