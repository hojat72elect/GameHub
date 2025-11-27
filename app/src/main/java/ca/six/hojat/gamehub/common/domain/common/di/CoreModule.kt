package ca.six.hojat.gamehub.common.domain.common.di

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CoreModule {

    @Binds
    fun bindDispatcherProvider(binding: DispatcherProviderImpl): DispatcherProvider
}
