package ca.six.hojat.gamehub.feature.likes.di

import ca.six.hojat.gamehub.feature.likes.domain.ObserveLikedGamesUseCase
import ca.six.hojat.gamehub.feature.likes.domain.ObserveLikedGamesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface LikesBindingsModule {

    @Binds
    @Singleton
    fun bindObserveLikedGamesUseCase(binding: ObserveLikedGamesUseCaseImpl): ObserveLikedGamesUseCase
}
