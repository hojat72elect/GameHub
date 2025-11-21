package com.paulrybitskyi.gamedge.feature.category.di

import com.paulrybitskyi.gamedge.common.domain.games.ObservableGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.RefreshableGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObserveComingSoonGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObserveMostAnticipatedGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObservePopularGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObserveRecentlyReleasedGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshComingSoonGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshMostAnticipatedGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshPopularGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.usecases.RefreshRecentlyReleasedGamesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityRetainedComponent::class)
internal object GamesCategoryModule {

    @Provides
    @IntoMap
    @GamesCategoryKey(GamesCategoryKey.Type.POPULAR)
    fun providePopularGamesObserverUseCase(
        observePopularGamesUseCase: ObservePopularGamesUseCase,
    ): ObservableGamesUseCase {
        return observePopularGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesCategoryKey(GamesCategoryKey.Type.POPULAR)
    fun providePopularGamesRefresherUseCase(
        refreshPopularGamesUseCase: RefreshPopularGamesUseCase,
    ): RefreshableGamesUseCase {
        return refreshPopularGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesCategoryKey(GamesCategoryKey.Type.RECENTLY_RELEASED)
    fun provideRecentlyReleasedGamesObserverUseCase(
        observeRecentlyReleasedGamesUseCase: ObserveRecentlyReleasedGamesUseCase,
    ): ObservableGamesUseCase {
        return observeRecentlyReleasedGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesCategoryKey(GamesCategoryKey.Type.RECENTLY_RELEASED)
    fun provideRecentlyReleasedGamesRefresherUseCase(
        refreshRecentlyReleasedGamesUseCase: RefreshRecentlyReleasedGamesUseCase,
    ): RefreshableGamesUseCase {
        return refreshRecentlyReleasedGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesCategoryKey(GamesCategoryKey.Type.COMING_SOON)
    fun provideComingSoonGamesObserverUseCase(
        observeComingSoonGamesUseCase: ObserveComingSoonGamesUseCase,
    ): ObservableGamesUseCase {
        return observeComingSoonGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesCategoryKey(GamesCategoryKey.Type.COMING_SOON)
    fun provideComingSoonGamesRefresherUseCase(
        refreshComingSoonGamesUseCase: RefreshComingSoonGamesUseCase,
    ): RefreshableGamesUseCase {
        return refreshComingSoonGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesCategoryKey(GamesCategoryKey.Type.MOST_ANTICIPATED)
    fun provideMostAnticipatedGamesObserverUseCase(
        observeMostAnticipatedGamesUseCase: ObserveMostAnticipatedGamesUseCase,
    ): ObservableGamesUseCase {
        return observeMostAnticipatedGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesCategoryKey(GamesCategoryKey.Type.MOST_ANTICIPATED)
    fun provideMostAnticipatedGamesRefresherUseCase(
        refreshMostAnticipatedGamesUseCase: RefreshMostAnticipatedGamesUseCase,
    ): RefreshableGamesUseCase {
        return refreshMostAnticipatedGamesUseCase
    }
}
