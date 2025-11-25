package com.paulrybitskyi.gamedge.feature.settings.di

import com.paulrybitskyi.gamedge.feature.settings.data.datastores.SettingsFileDataStoreImpl
import com.paulrybitskyi.gamedge.feature.settings.domain.datastores.SettingsLocalDataStore
import com.paulrybitskyi.gamedge.feature.settings.domain.usecases.ObserveSettingsUseCase
import com.paulrybitskyi.gamedge.feature.settings.domain.usecases.ObserveSettingsUseCaseImpl
import com.paulrybitskyi.gamedge.feature.settings.domain.usecases.ObserveThemeUseCase
import com.paulrybitskyi.gamedge.feature.settings.domain.usecases.ObserveThemeUseCaseImpl
import com.paulrybitskyi.gamedge.feature.settings.domain.usecases.SaveSettingsUseCase
import com.paulrybitskyi.gamedge.feature.settings.domain.usecases.SaveSettingsUseCaseImpl
import com.paulrybitskyi.gamedge.feature.settings.presentation.SettingsUiModelMapper
import com.paulrybitskyi.gamedge.feature.settings.presentation.SettingsUiModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface SettingsBindingsModule {

    @Binds
    @Singleton
    fun bindSettingsLocalDataStore(binding: SettingsFileDataStoreImpl): SettingsLocalDataStore

    @Binds
    @Singleton
    fun bindObserveThemeUseCase(binding: ObserveThemeUseCaseImpl): ObserveThemeUseCase

    @Binds
    @Singleton
    fun bindSaveSettingsUseCase(binding: SaveSettingsUseCaseImpl): SaveSettingsUseCase

    @Binds
    @Singleton
    fun bindObserveSettingsUseCase(binding: ObserveSettingsUseCaseImpl): ObserveSettingsUseCase
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface SettingsViewModelBindingsModule {

    @Binds
    fun bindSettingsUiModelMapper(binding: SettingsUiModelMapperImpl): SettingsUiModelMapper
}
