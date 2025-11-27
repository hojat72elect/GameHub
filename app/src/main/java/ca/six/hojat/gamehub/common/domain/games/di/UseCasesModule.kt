package ca.six.hojat.gamehub.common.domain.games.di

import ca.six.hojat.gamehub.common.domain.games.usecases.ObserveComingSoonGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.ObserveComingSoonGamesUseCaseImpl
import ca.six.hojat.gamehub.common.domain.games.usecases.ObserveMostAnticipatedGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.ObserveMostAnticipatedGamesUseCaseImpl
import ca.six.hojat.gamehub.common.domain.games.usecases.ObservePopularGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.ObservePopularGamesUseCaseImpl
import ca.six.hojat.gamehub.common.domain.games.usecases.ObserveRecentlyReleasedGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.ObserveRecentlyReleasedGamesUseCaseImpl
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshComingSoonGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshComingSoonGamesUseCaseImpl
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshMostAnticipatedGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshMostAnticipatedGamesUseCaseImpl
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshPopularGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshPopularGamesUseCaseImpl
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshRecentlyReleasedGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshRecentlyReleasedGamesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCasesModule {

    @Binds
    fun bindObserveComingSoonGamesUseCase(
        binding: ObserveComingSoonGamesUseCaseImpl,
    ): ObserveComingSoonGamesUseCase

    @Binds
    fun bindObserveMostAnticipatedGamesUseCase(
        binding: ObserveMostAnticipatedGamesUseCaseImpl,
    ): ObserveMostAnticipatedGamesUseCase

    @Binds
    fun bindObservePopularGamesUseCase(
        binding: ObservePopularGamesUseCaseImpl,
    ): ObservePopularGamesUseCase

    @Binds
    fun bindObserveRecentlyReleasedGamesUseCase(
        binding: ObserveRecentlyReleasedGamesUseCaseImpl,
    ): ObserveRecentlyReleasedGamesUseCase

    @Binds
    fun bindRefreshComingSoonGamesUseCase(
        binding: RefreshComingSoonGamesUseCaseImpl,
    ): RefreshComingSoonGamesUseCase

    @Binds
    fun bindRefreshMostAnticipatedGamesUseCase(
        binding: RefreshMostAnticipatedGamesUseCaseImpl,
    ): RefreshMostAnticipatedGamesUseCase

    @Binds
    fun bindRefreshPopularGamesUseCase(
        binding: RefreshPopularGamesUseCaseImpl,
    ): RefreshPopularGamesUseCase

    @Binds
    fun bindRefreshRecentlyReleasedGamesUseCase(
        binding: RefreshRecentlyReleasedGamesUseCaseImpl,
    ): RefreshRecentlyReleasedGamesUseCase
}
