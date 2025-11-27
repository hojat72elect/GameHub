package ca.six.hojat.gamehub.common.data.games.datastores.igdb

import ca.six.hojat.gamehub.common.domain.games.entities.AgeRating
import ca.six.hojat.gamehub.common.domain.games.entities.AgeRatingCategory
import ca.six.hojat.gamehub.common.domain.games.entities.AgeRatingType
import ca.six.hojat.gamehub.common.domain.games.entities.Category
import ca.six.hojat.gamehub.common.domain.games.entities.Company
import ca.six.hojat.gamehub.common.domain.games.entities.Game
import ca.six.hojat.gamehub.common.domain.games.entities.Genre
import ca.six.hojat.gamehub.common.domain.games.entities.Image
import ca.six.hojat.gamehub.common.domain.games.entities.InvolvedCompany
import ca.six.hojat.gamehub.common.domain.games.entities.Keyword
import ca.six.hojat.gamehub.common.domain.games.entities.Mode
import ca.six.hojat.gamehub.common.domain.games.entities.Platform
import ca.six.hojat.gamehub.common.domain.games.entities.PlayerPerspective
import ca.six.hojat.gamehub.common.domain.games.entities.ReleaseDate
import ca.six.hojat.gamehub.common.domain.games.entities.ReleaseDateCategory
import ca.six.hojat.gamehub.common.domain.games.entities.Theme
import ca.six.hojat.gamehub.common.domain.games.entities.Video
import ca.six.hojat.gamehub.common.domain.games.entities.Website
import ca.six.hojat.gamehub.common.domain.games.entities.WebsiteCategory
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiAgeRating
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiCategory
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiCompany
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiGame
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiGenre
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiImage
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiInvolvedCompany
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiKeyword
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiMode
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiPlatform
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiPlayerPerspective
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiReleaseDate
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiTheme
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiVideo
import ca.six.hojat.gamehub.igdb.api.games.entities.ApiWebsite
import javax.inject.Inject

internal class IgdbGameMapper @Inject constructor() {

    fun mapToDomainGame(apiGame: ApiGame): Game {
        return Game(
            id = apiGame.id,
            hypeCount = apiGame.hypeCount,
            releaseDate = apiGame.releaseDate,
            criticsRating = apiGame.criticsRating,
            usersRating = apiGame.usersRating,
            totalRating = apiGame.totalRating,
            name = apiGame.name,
            summary = apiGame.summary,
            storyline = apiGame.storyline,
            category = apiGame.category.toDomainCategory(),
            cover = apiGame.cover?.toDomainImage(),
            releaseDates = apiGame.releaseDates.toDomainReleaseDates(),
            ageRatings = apiGame.ageRatings.toDomainAgeRatings(),
            videos = apiGame.videos.toDomainVideos(),
            artworks = apiGame.artworks.toDomainImages(),
            screenshots = apiGame.screenshots.toDomainImages(),
            genres = apiGame.genres.toDomainGenres(),
            platforms = apiGame.platforms.toDomainPlatforms(),
            playerPerspectives = apiGame.playerPerspectives.toDomainPlayerPerspectives(),
            themes = apiGame.themes.toDomainThemes(),
            modes = apiGame.modes.toDomainModes(),
            keywords = apiGame.keywords.toDomainKeywords(),
            involvedCompanies = apiGame.involvedCompanies.toDomainInvolvedCompanies(),
            websites = apiGame.websites.toDomainWebsites(),
            similarGames = apiGame.similarGames,
        )
    }

    private fun ApiCategory.toDomainCategory(): Category {
        return Category.valueOf(name)
    }

    private fun ApiImage.toDomainImage(): Image {
        return Image(
            id = id,
            width = width,
            height = height,
        )
    }

    private fun List<ApiImage>.toDomainImages(): List<Image> {
        return map { it.toDomainImage() }
    }

    private fun List<ApiVideo>.toDomainVideos(): List<Video> {
        return map {
            Video(
                id = it.id,
                name = it.name,
            )
        }
    }

    private fun List<ApiReleaseDate>.toDomainReleaseDates(): List<ReleaseDate> {
        return map {
            ReleaseDate(
                date = it.date,
                year = it.year,
                category = ReleaseDateCategory.valueOf(it.category.name),
            )
        }
    }

    private fun List<ApiAgeRating>.toDomainAgeRatings(): List<AgeRating> {
        return map {
            AgeRating(
                category = AgeRatingCategory.valueOf(it.category.name),
                type = AgeRatingType.valueOf(it.type.name),
            )
        }
    }

    private fun List<ApiGenre>.toDomainGenres(): List<Genre> {
        return map {
            Genre(
                name = it.name,
            )
        }
    }

    private fun List<ApiPlatform>.toDomainPlatforms(): List<Platform> {
        return map {
            Platform(
                abbreviation = it.abbreviation,
                name = it.name,
            )
        }
    }

    private fun List<ApiPlayerPerspective>.toDomainPlayerPerspectives(): List<PlayerPerspective> {
        return map {
            PlayerPerspective(
                name = it.name,
            )
        }
    }

    private fun List<ApiTheme>.toDomainThemes(): List<Theme> {
        return map {
            Theme(
                name = it.name,
            )
        }
    }

    private fun List<ApiMode>.toDomainModes(): List<Mode> {
        return map {
            Mode(
                name = it.name,
            )
        }
    }

    private fun List<ApiKeyword>.toDomainKeywords(): List<Keyword> {
        return map {
            Keyword(
                name = it.name,
            )
        }
    }

    private fun List<ApiInvolvedCompany>.toDomainInvolvedCompanies(): List<InvolvedCompany> {
        return map {
            InvolvedCompany(
                company = it.company.toDomainCompany(),
                isDeveloper = it.isDeveloper,
                isPublisher = it.isPublisher,
                isPorter = it.isPorter,
                isSupporting = it.isSupporting,
            )
        }
    }

    private fun ApiCompany.toDomainCompany(): Company {
        return Company(
            id = id,
            name = name,
            websiteUrl = websiteUrl,
            logo = logo?.toDomainImage(),
            developedGames = developedGames,
        )
    }

    private fun List<ApiWebsite>.toDomainWebsites(): List<Website> {
        return map {
            Website(
                id = it.id,
                url = it.url,
                category = WebsiteCategory.valueOf(it.category.name),
            )
        }
    }
}

internal fun IgdbGameMapper.mapToDomainGames(apiGames: List<ApiGame>): List<Game> {
    return apiGames.map(::mapToDomainGame)
}
