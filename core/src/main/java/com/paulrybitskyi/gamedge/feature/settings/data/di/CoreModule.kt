package com.paulrybitskyi.gamedge.feature.settings.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.paulrybitskyi.gamedge.feature.settings.data.datastores.ProtoSettings
import com.paulrybitskyi.gamedge.feature.settings.data.datastores.ProtoSettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private const val SETTINGS_PREFERENCES_DATA_STORE_NAME = "settings.pb"

private val Context.settingsProtoDataStore by dataStore(
    fileName = SETTINGS_PREFERENCES_DATA_STORE_NAME,
    serializer = ProtoSettingsSerializer,
)

@Module
@InstallIn(SingletonComponent::class)
internal object CoreModule {

    @Provides
    fun provideSettingsProtoDataStore(
        @ApplicationContext context: Context,
    ): DataStore<ProtoSettings> {
        return context.settingsProtoDataStore
    }
}
