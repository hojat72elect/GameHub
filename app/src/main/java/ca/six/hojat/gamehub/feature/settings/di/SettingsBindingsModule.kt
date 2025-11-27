package ca.six.hojat.gamehub.feature.settings.di

import ca.six.hojat.gamehub.feature.settings.data.datastores.SettingsFileDataStoreImpl
import ca.six.hojat.gamehub.feature.settings.domain.datastores.SettingsLocalDataStore
import ca.six.hojat.gamehub.feature.settings.domain.usecases.ObserveSettingsUseCase
import ca.six.hojat.gamehub.feature.settings.domain.usecases.ObserveSettingsUseCaseImpl
import ca.six.hojat.gamehub.feature.settings.domain.usecases.ObserveThemeUseCase
import ca.six.hojat.gamehub.feature.settings.domain.usecases.ObserveThemeUseCaseImpl
import ca.six.hojat.gamehub.feature.settings.domain.usecases.SaveSettingsUseCase
import ca.six.hojat.gamehub.feature.settings.domain.usecases.SaveSettingsUseCaseImpl
import ca.six.hojat.gamehub.feature.settings.presentation.SettingsUiModelMapper
import ca.six.hojat.gamehub.feature.settings.presentation.SettingsUiModelMapperImpl
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
