package ca.six.hojat.gamehub.database.articles

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import ca.six.hojat.gamehub.core.JsonConverter
import ca.six.hojat.gamehub.database.articles.entities.DbImageType
import ca.six.hojat.gamehub.database.common.RoomTypeConverter
import javax.inject.Inject

@ProvidedTypeConverter
internal class ArticlesTypeConverter @Inject constructor(
    private val jsonConverter: JsonConverter,
) : RoomTypeConverter {

    @TypeConverter
    fun fromImageUrls(imageUrls: Map<DbImageType, String>): String {
        return jsonConverter.toJson(imageUrls)
    }

    @TypeConverter
    fun toImageUrls(json: String): Map<DbImageType, String> {
        return (jsonConverter.fromJson(json) ?: emptyMap())
    }
}
