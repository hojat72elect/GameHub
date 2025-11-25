package com.paulrybitskyi.gamedge.feature.info.di

import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetCompanyDevelopedGamesUseCase
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetCompanyDevelopedGamesUseCaseImpl
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetGameUseCase
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetGameUseCaseImpl
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetSimilarGamesUseCase
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetSimilarGamesUseCaseImpl
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.likes.ObserveGameLikeStateUseCase
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.likes.ObserveGameLikeStateUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface GamesInfoBindingsModule {

    @Binds
    @Singleton
    fun bindGetCompanyDevelopedGamesUseCase(
        binding: GetCompanyDevelopedGamesUseCaseImpl,
    ): GetCompanyDevelopedGamesUseCase

    @Binds
    @Singleton
    fun bindGetGameUseCase(binding: GetGameUseCaseImpl): GetGameUseCase

    @Binds
    @Singleton
    fun bindGetSimilarGamesUseCase(binding: GetSimilarGamesUseCaseImpl): GetSimilarGamesUseCase

    @Binds
    @Singleton
    fun bindObserveGameLikeStateUseCase(
        binding: ObserveGameLikeStateUseCaseImpl,
    ): ObserveGameLikeStateUseCase
}
