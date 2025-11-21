
package com.paulrybitskyi.gamedge.feature.settings.domain.datastores

import com.paulrybitskyi.gamedge.feature.settings.domain.entities.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataStore {
    suspend fun saveSettings(settings: Settings)
    fun observeSettings(): Flow<Settings>
}
