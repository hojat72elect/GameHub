package ca.six.hojat.gamehub.di

import ca.six.hojat.gamehub.initializers.CompositeInitializer
import ca.six.hojat.gamehub.initializers.ImageLoaderDelegateInitializer
import ca.six.hojat.gamehub.initializers.Initializer
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
