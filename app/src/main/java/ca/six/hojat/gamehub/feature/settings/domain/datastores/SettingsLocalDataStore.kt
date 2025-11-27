package ca.six.hojat.gamehub.feature.settings.domain.datastores

import ca.six.hojat.gamehub.feature.settings.domain.entities.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataStore {
    suspend fun saveSettings(settings: Settings)
    fun observeSettings(): Flow<Settings>
}
