package ca.six.hojat.gamehub.shared.data.local.games

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import ca.six.hojat.gamehub.core.JsonConverter
import ca.six.hojat.gamehub.shared.data.local.RoomTypeConverter
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalAgeRating
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalAgeRatingCategory
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalAgeRatingType
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalCategory
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalGenre
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalImage
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalInvolvedCompany
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalKeyword
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalMode
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalPlatform
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalPlayerPerspective
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalReleaseDate
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalReleaseDateCategory
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalTheme
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalVideo
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalWebsite
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalWebsiteCategory
import javax.inject.Inject

@ProvidedTypeConverter
@Suppress("TooManyFunctions")
internal class GamesTypeConverter @Inject constructor(
    private val jsonConverter: JsonConverter,
) : RoomTypeConverter {

    @TypeConverter
    fun fromCategory(category: LocalCategory): String {
        return jsonConverter.toJson(category)
    }

    @TypeConverter
    fun toCategory(json: String): LocalCategory {
        return (jsonConverter.fromJson(json) ?: LocalCategory.UNKNOWN)
    }

    @TypeConverter
    fun fromImage(image: LocalImage?): String {
        return jsonConverter.toJson(image)
    }

    @TypeConverter
    fun toImage(json: String): LocalImage? {
        return jsonConverter.fromJson(json)
    }

    @TypeConverter
    fun fromImages(images: List<LocalImage>): String {
        return jsonConverter.toJson(images)
    }

    @TypeConverter
    fun toImages(json: String): List<LocalImage> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromReleaseDates(releaseDates: List<LocalReleaseDate>): String {
        return jsonConverter.toJson(releaseDates)
    }

    @TypeConverter
    fun toReleaseDates(json: String): List<LocalReleaseDate> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromReleaseDateCategory(category: LocalReleaseDateCategory): String {
        return jsonConverter.toJson(category)
    }

    @TypeConverter
    fun toReleaseDateCategory(json: String): LocalReleaseDateCategory {
        return (jsonConverter.fromJson(json) ?: LocalReleaseDateCategory.UNKNOWN)
    }

    @TypeConverter
    fun fromAgeRatings(ageRatings: List<LocalAgeRating>): String {
        return jsonConverter.toJson(ageRatings)
    }

    @TypeConverter
    fun toAgeRatings(json: String): List<LocalAgeRating> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromAgeRatingCategory(category: LocalAgeRatingCategory): String {
        return jsonConverter.toJson(category)
    }

    @TypeConverter
    fun toAgeRatingCategory(json: String): LocalAgeRatingCategory {
        return (jsonConverter.fromJson(json) ?: LocalAgeRatingCategory.UNKNOWN)
    }

    @TypeConverter
    fun fromAgeRatingType(type: LocalAgeRatingType): String {
        return jsonConverter.toJson(type)
    }

    @TypeConverter
    fun toAgeRatingType(json: String): LocalAgeRatingType {
        return (jsonConverter.fromJson(json) ?: LocalAgeRatingType.UNKNOWN)
    }

    @TypeConverter
    fun fromVideos(videos: List<LocalVideo>): String {
        return jsonConverter.toJson(videos)
    }

    @TypeConverter
    fun toVideos(json: String): List<LocalVideo> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromGenres(genres: List<LocalGenre>): String {
        return jsonConverter.toJson(genres)
    }

    @TypeConverter
    fun toGenres(json: String): List<LocalGenre> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromPlatforms(platforms: List<LocalPlatform>): String {
        return jsonConverter.toJson(platforms)
    }

    @TypeConverter
    fun toPlatforms(json: String): List<LocalPlatform> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromPlayerPerspectives(playerPerspectives: List<LocalPlayerPerspective>): String {
        return jsonConverter.toJson(playerPerspectives)
    }

    @TypeConverter
    fun toPlayerPerspectives(json: String): List<LocalPlayerPerspective> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromThemes(themes: List<LocalTheme>): String {
        return jsonConverter.toJson(themes)
    }

    @TypeConverter
    fun toThemes(json: String): List<LocalTheme> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromModes(modes: List<LocalMode>): String {
        return jsonConverter.toJson(modes)
    }

    @TypeConverter
    fun toModes(json: String): List<LocalMode> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromKeywords(keywords: List<LocalKeyword>): String {
        return jsonConverter.toJson(keywords)
    }

    @TypeConverter
    fun toKeywords(json: String): List<LocalKeyword> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromInvolvedCompanies(involvedCompanies: List<LocalInvolvedCompany>): String {
        return jsonConverter.toJson(involvedCompanies)
    }

    @TypeConverter
    fun toInvolvedCompanies(json: String): List<LocalInvolvedCompany> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromWebsites(websites: List<LocalWebsite>): String {
        return jsonConverter.toJson(websites)
    }

    @TypeConverter
    fun toWebsites(json: String): List<LocalWebsite> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }

    @TypeConverter
    fun fromWebsiteCategory(category: LocalWebsiteCategory): String {
        return jsonConverter.toJson(category)
    }

    @TypeConverter
    fun toWebsiteCategory(json: String): LocalWebsiteCategory {
        return (jsonConverter.fromJson(json) ?: LocalWebsiteCategory.UNKNOWN)
    }

    @TypeConverter
    fun fromSimilarGames(similarGames: List<Int>): String {
        return jsonConverter.toJson(similarGames)
    }

    @TypeConverter
    fun toSimilarGames(json: String): List<Int> {
        return (jsonConverter.fromJson(json) ?: emptyList())
    }
}