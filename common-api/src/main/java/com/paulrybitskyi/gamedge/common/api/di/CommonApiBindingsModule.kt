package com.paulrybitskyi.gamedge.common.api.di

import com.paulrybitskyi.gamedge.common.api.UserAgentProvider
import com.paulrybitskyi.gamedge.common.api.UserAgentProviderImpl
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
