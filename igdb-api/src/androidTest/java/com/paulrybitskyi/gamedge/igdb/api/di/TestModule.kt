package com.paulrybitskyi.gamedge.igdb.api.di

import com.paulrybitskyi.gamedge.common.testing.di.MocksModule
import com.paulrybitskyi.gamedge.igdb.api.common.CredentialsStore
import com.paulrybitskyi.gamedge.igdb.api.utils.FakeCredentialsStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [MocksModule::class])
@InstallIn(SingletonComponent::class)
internal interface TestModule {

    @Binds
    fun bind(binding: FakeCredentialsStore): CredentialsStore
}
