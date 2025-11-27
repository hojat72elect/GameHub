package ca.six.hojat.gamehub.common.ui.di

import ca.six.hojat.gamehub.common.ui.TransitionAnimations
import ca.six.hojat.gamehub.common.ui.di.qualifiers.TransitionAnimationDuration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CommonUiModule {

    @Provides
    @Singleton
    @TransitionAnimationDuration
    fun provideTransitionAnimationDuration(): Long {
        return TransitionAnimations.DefaultAnimationDuration.toLong()
    }
}
