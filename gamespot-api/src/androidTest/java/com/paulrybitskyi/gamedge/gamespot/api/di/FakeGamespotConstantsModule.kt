package com.paulrybitskyi.gamedge.gamespot.api.di

import com.paulrybitskyi.gamedge.gamespot.api.common.GamespotConstantsProvider
import com.paulrybitskyi.gamedge.gamespot.api.common.di.GamespotConstantsModule
import com.paulrybitskyi.gamedge.gamespot.api.utils.FakeGamespotConstantsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [GamespotConstantsModule::class],
)
internal interface FakeGamespotConstantsModule {

    @Binds
    fun bindGamespotConstantsProvider(binding: FakeGamespotConstantsProvider): GamespotConstantsProvider
}
