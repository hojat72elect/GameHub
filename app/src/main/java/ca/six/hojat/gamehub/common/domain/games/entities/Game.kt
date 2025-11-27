package ca.six.hojat.gamehub.common.domain.games.entities

data class Game(
    val id: Int,
    val hypeCount: Int?,
    val releaseDate: Long?,
    val criticsRating: Double?,
    val usersRating: Double?,
    val totalRating: Double?,
    val name: String,
    val summary: String?,
    val storyline: String?,
    val category: Category,
    val cover: Image?,
    val releaseDates: List<ReleaseDate>,
    val ageRatings: List<AgeRating>,
    val videos: List<Video>,
    val artworks: List<Image>,
    val screenshots: List<Image>,
    val genres: List<Genre>,
    val platforms: List<Platform>,
    val playerPerspectives: List<PlayerPerspective>,
    val themes: List<Theme>,
    val modes: List<Mode>,
    val keywords: List<Keyword>,
    val involvedCompanies: List<InvolvedCompany>,
    val websites: List<Website>,
    val similarGames: List<Int>,
) {

    val hasSimilarGames: Boolean
        get() = similarGames.isNotEmpty()

    val developerCompany: Company?
        get() = involvedCompanies.firstOrNull(InvolvedCompany::isDeveloper)?.company
}
