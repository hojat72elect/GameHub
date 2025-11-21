package com.paulrybitskyi.gamedge.database.common.di

import android.content.Context
import androidx.room.Room
import com.paulrybitskyi.gamedge.database.Constants
import com.paulrybitskyi.gamedge.database.GamedgeDatabase
import com.paulrybitskyi.gamedge.database.common.MANUAL_MIGRATIONS
import com.paulrybitskyi.gamedge.database.common.RoomTypeConverter
import com.paulrybitskyi.gamedge.database.common.addTypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    @Suppress("SpreadOperator")
    fun provideGamedgeDatabase(
        @ApplicationContext context: Context,
        typeConverters: Set<@JvmSuppressWildcards RoomTypeConverter>,
    ): GamedgeDatabase {
        return Room.databaseBuilder(
            context,
            GamedgeDatabase::class.java,
            Constants.DATABASE_NAME,
        )
            .addTypeConverters(typeConverters)
            .addMigrations(*MANUAL_MIGRATIONS)
            .build()
    }
}
