package ca.six.hojat.gamehub.shared.data.local.di

import androidx.room.RoomDatabase
import ca.six.hojat.gamehub.shared.data.local.RoomTypeConverter

internal fun <T : RoomDatabase> RoomDatabase.Builder<T>.addTypeConverters(
    typeConverters: Set<RoomTypeConverter>,
): RoomDatabase.Builder<T> = apply {
    typeConverters.forEach(::addTypeConverter)
}
