
package com.paulrybitskyi.gamedge.common.domain.games.di

import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObserveComingSoonGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObserveComingSoonGamesUseCaseImpl
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObserveMostAnticipatedGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObserveMostAnticipatedGamesUseCaseImpl
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObservePopularGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObservePopularGamesUseCaseImpl
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObserveRecentlyReleasedGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObserveRecentlyReleasedGamesUseCaseImpl
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshComingSoonGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshComingSoonGamesUseCaseImpl
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshMostAnticipatedGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshMostAnticipatedGamesUseCaseImpl
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshPopularGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshPopularGamesUseCaseImpl
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshRecentlyReleasedGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshRecentlyReleasedGamesUseCaseImpl
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
