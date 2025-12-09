package ca.six.hojat.gamehub.shared.data.local.settings.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.six.hojat.gamehub.shared.data.local.settings.entities.LocalSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsTable {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: LocalSettings)

    @Query("SELECT * FROM settings LIMIT 1")
    fun observeSettings(): Flow<LocalSettings?>
}
