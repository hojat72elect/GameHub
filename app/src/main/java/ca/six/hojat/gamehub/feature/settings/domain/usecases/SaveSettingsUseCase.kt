package ca.six.hojat.gamehub.feature.settings.domain.usecases

import ca.six.hojat.gamehub.common.domain.common.usecases.UseCase
import ca.six.hojat.gamehub.feature.settings.domain.datastores.SettingsLocalDataStore
import ca.six.hojat.gamehub.feature.settings.domain.entities.Settings
import javax.inject.Inject
import javax.inject.Singleton

internal interface SaveSettingsUseCase : UseCase<Settings, Unit>

@Singleton
internal class SaveSettingsUseCaseImpl @Inject constructor(
    private val localDataStore: SettingsLocalDataStore,
) : SaveSettingsUseCase {

    override suspend fun execute(params: Settings) {
        localDataStore.saveSettings(params)
    }
}
