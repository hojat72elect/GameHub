package com.paulrybitskyi.gamedge.feature.settings.domain.usecases

import com.paulrybitskyi.gamedge.common.domain.common.usecases.UseCase
import com.paulrybitskyi.gamedge.feature.settings.domain.datastores.SettingsLocalDataStore
import com.paulrybitskyi.gamedge.feature.settings.domain.entities.Settings
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject
import javax.inject.Singleton

internal interface SaveSettingsUseCase : UseCase<Settings, Unit>

@Singleton
@BindType
internal class SaveSettingsUseCaseImpl @Inject constructor(
    private val localDataStore: SettingsLocalDataStore,
) : SaveSettingsUseCase {

    override suspend fun execute(params: Settings) {
        localDataStore.saveSettings(params)
    }
}
