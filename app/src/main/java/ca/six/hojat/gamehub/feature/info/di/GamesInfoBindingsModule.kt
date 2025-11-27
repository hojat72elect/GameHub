package ca.six.hojat.gamehub.feature.info.di

import ca.six.hojat.gamehub.feature.info.domain.usecases.GetCompanyDevelopedGamesUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetCompanyDevelopedGamesUseCaseImpl
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameUseCaseImpl
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetSimilarGamesUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetSimilarGamesUseCaseImpl
import ca.six.hojat.gamehub.feature.info.domain.usecases.likes.ObserveGameLikeStateUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.likes.ObserveGameLikeStateUseCaseImpl
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
