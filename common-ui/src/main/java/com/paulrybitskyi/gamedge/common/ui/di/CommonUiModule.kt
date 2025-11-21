
package com.paulrybitskyi.gamedge.common.ui.di

import com.paulrybitskyi.gamedge.common.ui.TransitionAnimations
import com.paulrybitskyi.gamedge.common.ui.di.qualifiers.TransitionAnimationDuration
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
