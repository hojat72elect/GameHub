package ca.six.hojat.gamehub.shared.data.local.news_articles

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import ca.six.hojat.gamehub.core.JsonConverter
import ca.six.hojat.gamehub.shared.data.local.RoomTypeConverter
import ca.six.hojat.gamehub.shared.data.local.news_articles.entities.LocalImageType
import javax.inject.Inject

@ProvidedTypeConverter
internal class NewsArticlesTypeConverter @Inject constructor(
    private val jsonConverter: JsonConverter,
) : RoomTypeConverter {

    @TypeConverter
    fun fromImageUrls(imageUrls: Map<LocalImageType, String>): String {
        return jsonConverter.toJson(imageUrls)
    }

    @TypeConverter
    fun toImageUrls(json: String): Map<LocalImageType, String> {
        return (jsonConverter.fromJson(json) ?: emptyMap())
    }
}
