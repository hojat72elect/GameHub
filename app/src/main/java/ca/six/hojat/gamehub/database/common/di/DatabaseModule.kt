package ca.six.hojat.gamehub.database.common.di

import android.content.Context
import androidx.room.Room
import ca.six.hojat.gamehub.database.Constants
import ca.six.hojat.gamehub.database.GamedgeDatabase
import ca.six.hojat.gamehub.database.common.MANUAL_MIGRATIONS
import ca.six.hojat.gamehub.database.common.RoomTypeConverter
import ca.six.hojat.gamehub.database.common.addTypeConverters
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
