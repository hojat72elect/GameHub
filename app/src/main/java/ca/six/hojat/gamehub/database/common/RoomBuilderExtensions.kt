package ca.six.hojat.gamehub.database.common

import androidx.room.RoomDatabase

internal fun <T : RoomDatabase> RoomDatabase.Builder<T>.addTypeConverters(
    typeConverters: Set<RoomTypeConverter>,
): RoomDatabase.Builder<T> = apply {
    typeConverters.forEach(::addTypeConverter)
}
