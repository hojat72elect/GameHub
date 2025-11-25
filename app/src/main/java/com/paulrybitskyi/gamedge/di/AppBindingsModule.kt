package com.paulrybitskyi.gamedge.di

import com.paulrybitskyi.gamedge.initializers.CompositeInitializer
import com.paulrybitskyi.gamedge.initializers.ImageLoaderDelegateInitializer
import com.paulrybitskyi.gamedge.initializers.Initializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface AppBindingsModule {

    @Binds
    @Singleton
    fun bindCompositeInitializer(binding: CompositeInitializer): Initializer

    @Binds
    @IntoSet
    fun bindImageLoaderDelegateInitializer(binding: ImageLoaderDelegateInitializer): Initializer
}
