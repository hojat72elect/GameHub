package ca.six.hojat.gamehub.feature.settings.data.datastores

import androidx.datastore.core.DataStore
import ca.six.hojat.gamehub.feature.settings.domain.datastores.SettingsLocalDataStore
import ca.six.hojat.gamehub.feature.settings.domain.entities.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SettingsFileDataStoreImpl @Inject constructor(
    private val protoDataStore: DataStore<ProtoSettings>,
    private val protoMapper: ProtoSettingsMapper,
) : SettingsLocalDataStore {

    override suspend fun saveSettings(settings: Settings) {
        protoDataStore.updateData {
            protoMapper.mapToProtoSettings(settings)
        }
    }

    override fun observeSettings(): Flow<Settings> {
        return protoDataStore.data
            .map(protoMapper::mapToDomainSettings)
    }
}
