package ca.six.hojat.gamehub.feature.discovery.di

import ca.six.hojat.gamehub.common.domain.games.ObservableGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.RefreshableGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.ObserveComingSoonGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.ObserveMostAnticipatedGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.ObservePopularGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.ObserveRecentlyReleasedGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshComingSoonGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshMostAnticipatedGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshPopularGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.usecases.RefreshRecentlyReleasedGamesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityRetainedComponent::class)
internal object GamesDiscoveryModule {

    @Provides
    @IntoMap
    @GamesDiscoveryKey(GamesDiscoveryKey.Type.POPULAR)
    fun providePopularGamesObserverUseCase(
        observePopularGamesUseCase: ObservePopularGamesUseCase,
    ): ObservableGamesUseCase {
        return observePopularGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesDiscoveryKey(GamesDiscoveryKey.Type.POPULAR)
    fun providePopularGamesRefresherUseCase(
        refreshPopularGamesUseCase: RefreshPopularGamesUseCase,
    ): RefreshableGamesUseCase {
        return refreshPopularGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesDiscoveryKey(GamesDiscoveryKey.Type.RECENTLY_RELEASED)
    fun provideRecentlyReleasedGamesObserverUseCase(
        observeRecentlyReleasedGamesUseCase: ObserveRecentlyReleasedGamesUseCase,
    ): ObservableGamesUseCase {
        return observeRecentlyReleasedGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesDiscoveryKey(GamesDiscoveryKey.Type.RECENTLY_RELEASED)
    fun provideRecentlyReleasedGamesRefresherUseCase(
        refreshRecentlyReleasedGamesUseCase: RefreshRecentlyReleasedGamesUseCase,
    ): RefreshableGamesUseCase {
        return refreshRecentlyReleasedGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesDiscoveryKey(GamesDiscoveryKey.Type.COMING_SOON)
    fun provideComingSoonGamesObserverUseCase(
        observeComingSoonGamesUseCase: ObserveComingSoonGamesUseCase,
    ): ObservableGamesUseCase {
        return observeComingSoonGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesDiscoveryKey(GamesDiscoveryKey.Type.COMING_SOON)
    fun provideComingSoonGamesRefresherUseCase(
        refreshComingSoonGamesUseCase: RefreshComingSoonGamesUseCase,
    ): RefreshableGamesUseCase {
        return refreshComingSoonGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesDiscoveryKey(GamesDiscoveryKey.Type.MOST_ANTICIPATED)
    fun provideMostAnticipatedGamesObserverUseCase(
        observeMostAnticipatedGamesUseCase: ObserveMostAnticipatedGamesUseCase,
    ): ObservableGamesUseCase {
        return observeMostAnticipatedGamesUseCase
    }

    @Provides
    @IntoMap
    @GamesDiscoveryKey(GamesDiscoveryKey.Type.MOST_ANTICIPATED)
    fun provideMostAnticipatedGamesRefresherUseCase(
        refreshMostAnticipatedGamesUseCase: RefreshMostAnticipatedGamesUseCase,
    ): RefreshableGamesUseCase {
        return refreshMostAnticipatedGamesUseCase
    }
}
