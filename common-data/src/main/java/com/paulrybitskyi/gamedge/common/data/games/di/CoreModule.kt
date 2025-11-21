package com.paulrybitskyi.gamedge.common.data.games.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private const val GAMES_PREFERENCES_NAME = "games"

private val Context.gamesPreferences by preferencesDataStore(GAMES_PREFERENCES_NAME)

@Module
@InstallIn(SingletonComponent::class)
internal object CoreModule {

    @Provides
    fun provideGamesPreferences(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.gamesPreferences
    }
}
