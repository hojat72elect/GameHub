package ca.six.hojat.gamehub.feature.settings.data.datastores

import ca.six.hojat.gamehub.feature.settings.domain.datastores.SettingsLocalDataStore
import ca.six.hojat.gamehub.feature.settings.domain.entities.Settings
import ca.six.hojat.gamehub.feature.settings.domain.entities.Theme
import ca.six.hojat.gamehub.shared.data.local.GameHubDatabase
import ca.six.hojat.gamehub.shared.data.local.settings.entities.LocalSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SettingsDatabaseDataStore @Inject constructor(
    private val database: GameHubDatabase
) : SettingsLocalDataStore {

    override suspend fun saveSettings(settings: Settings) {
        database.settingsTable.saveSettings(
            LocalSettings(themeName = settings.theme.name)
        )
    }

    override fun observeSettings(): Flow<Settings> {
        return database.settingsTable.observeSettings()
            .map { dbSettings ->
                if (dbSettings != null) {
                    Settings(theme = Theme.valueOf(dbSettings.themeName))
                } else {
                    Settings.DEFAULT
                }
            }
            .distinctUntilChanged()
    }
}
