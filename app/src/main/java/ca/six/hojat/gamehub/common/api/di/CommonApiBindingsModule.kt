package ca.six.hojat.gamehub.common.api.di

import ca.six.hojat.gamehub.common.api.UserAgentProvider
import ca.six.hojat.gamehub.common.api.UserAgentProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CommonApiBindingsModule {

    @Binds
    fun bindUserAgentProvider(binding: UserAgentProviderImpl): UserAgentProvider
}
