package ca.six.hojat.gamehub.shared.data.local.games.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = LocalGame.Schema.TABLE_NAME,
    primaryKeys = [LocalGame.Schema.ID],
    indices = [
        Index(LocalGame.Schema.HYPE_COUNT),
        Index(LocalGame.Schema.RELEASE_DATE),
        Index(LocalGame.Schema.USERS_RATING),
        Index(LocalGame.Schema.TOTAL_RATING),
        Index(LocalGame.Schema.NAME),
    ],
)
data class LocalGame(
    @ColumnInfo(name = Schema.ID) val id: Int,
    @ColumnInfo(name = Schema.HYPE_COUNT) val hypeCount: Int?,
    @ColumnInfo(name = Schema.RELEASE_DATE) val releaseDate: Long?,
    @ColumnInfo(name = Schema.CRITICS_RATING) val criticsRating: Double?,
    @ColumnInfo(name = Schema.USERS_RATING) val usersRating: Double?,
    @ColumnInfo(name = Schema.TOTAL_RATING) val totalRating: Double?,
    @ColumnInfo(name = Schema.NAME) val name: String,
    @ColumnInfo(name = Schema.SUMMARY) val summary: String?,
    @ColumnInfo(name = Schema.STORYLINE) val storyline: String?,
    @ColumnInfo(name = Schema.CATEGORY) val category: LocalCategory,
    @ColumnInfo(name = Schema.COVER) val cover: LocalImage?,
    @ColumnInfo(name = Schema.RELEASE_DATES) val releaseDates: List<LocalReleaseDate>,
    @ColumnInfo(name = Schema.AGE_RATINGS) val ageRatings: List<LocalAgeRating>,
    @ColumnInfo(name = Schema.VIDEOS) val videos: List<LocalVideo>,
    @ColumnInfo(name = Schema.ARTWORKS) val artworks: List<LocalImage>,
    @ColumnInfo(name = Schema.SCREENSHOTS) val screenshots: List<LocalImage>,
    @ColumnInfo(name = Schema.GENRES) val genres: List<LocalGenre>,
    @ColumnInfo(name = Schema.PLATFORMS) val platforms: List<LocalPlatform>,
    @ColumnInfo(name = Schema.PLAYER_PERSPECTIVES) val playerPerspectives: List<LocalPlayerPerspective>,
    @ColumnInfo(name = Schema.THEMES) val themes: List<LocalTheme>,
    @ColumnInfo(name = Schema.MODES) val modes: List<LocalMode>,
    @ColumnInfo(name = Schema.KEYWORDS) val keywords: List<LocalKeyword>,
    @ColumnInfo(name = Schema.INVOLVED_COMPANIES) val involvedCompanies: List<LocalInvolvedCompany>,
    @ColumnInfo(name = Schema.WEBSITES) val websites: List<LocalWebsite>,
    @ColumnInfo(name = Schema.SIMILAR_GAMES) val similarGames: List<Int>,
) {

    object Schema {
        const val TABLE_NAME = "games"
        const val ID = "id"
        const val HYPE_COUNT = "hype_count"
        const val RELEASE_DATE = "release_date"
        const val CRITICS_RATING = "critics_rating"
        const val USERS_RATING = "users_rating"
        const val TOTAL_RATING = "total_rating"
        const val NAME = "name"
        const val SUMMARY = "summary"
        const val STORYLINE = "storyline"
        const val CATEGORY = "category"
        const val COVER = "cover"
        const val RELEASE_DATES = "release_dates"
        const val AGE_RATINGS = "age_ratings"
        const val VIDEOS = "videos"
        const val ARTWORKS = "artworks"
        const val SCREENSHOTS = "screenshots"
        const val GENRES = "genres"
        const val PLATFORMS = "platforms"
        const val PLAYER_PERSPECTIVES = "player_perspectives"
        const val THEMES = "themes"
        const val MODES = "modes"
        const val KEYWORDS = "keywords"
        const val INVOLVED_COMPANIES = "involved_companies"
        const val WEBSITES = "websites"
        const val SIMILAR_GAMES = "similar_games"
    }
}
