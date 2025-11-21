
package com.paulrybitskyi.gamedge.database.tables

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.paulrybitskyi.gamedge.database.GamedgeDatabase
import com.paulrybitskyi.gamedge.database.common.MANUAL_MIGRATIONS
import com.paulrybitskyi.gamedge.database.common.RoomTypeConverter
import com.paulrybitskyi.gamedge.database.common.addTypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideGamedgeDatabase(
        typeConverters: Set<@JvmSuppressWildcards RoomTypeConverter>,
    ): GamedgeDatabase {
        return Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GamedgeDatabase::class.java,
        )
        .addTypeConverters(typeConverters)
        .addMigrations(*MANUAL_MIGRATIONS)
        .build()
    }
}
