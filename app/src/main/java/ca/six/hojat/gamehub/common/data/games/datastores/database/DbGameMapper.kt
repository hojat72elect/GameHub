package ca.six.hojat.gamehub.common.data.games.datastores.database

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
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalAgeRating
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalAgeRatingCategory
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalAgeRatingType
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalCategory
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalCompany
import ca.six.hojat.gamehub.shared.data.local.games.entities.LocalGame
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

internal class DbGameMapper @Inject constructor() {

    fun mapToDomainGame(localGame: LocalGame): Game {
        return Game(
            id = localGame.id,
            hypeCount = localGame.hypeCount,
            releaseDate = localGame.releaseDate,
            criticsRating = localGame.criticsRating,
            usersRating = localGame.usersRating,
            totalRating = localGame.totalRating,
            name = localGame.name,
            summary = localGame.summary,
            storyline = localGame.storyline,
            category = localGame.category.toDomainCategory(),
            cover = localGame.cover?.toDomainImage(),
            releaseDates = localGame.releaseDates.toDomainReleaseDates(),
            ageRatings = localGame.ageRatings.toDomainAgeRatings(),
            videos = localGame.videos.toDomainVideos(),
            artworks = localGame.artworks.toDomainImages(),
            screenshots = localGame.screenshots.toDomainImages(),
            genres = localGame.genres.toDomainGenres(),
            platforms = localGame.platforms.toDomainPlatforms(),
            playerPerspectives = localGame.playerPerspectives.toDomainPlayerPerspectives(),
            themes = localGame.themes.toDomainThemes(),
            modes = localGame.modes.toDomainModes(),
            keywords = localGame.keywords.toDomainKeywords(),
            involvedCompanies = localGame.involvedCompanies.toDomainInvolvedCompanies(),
            websites = localGame.websites.toDomainWebsites(),
            similarGames = localGame.similarGames,
        )
    }

    private fun LocalCategory.toDomainCategory(): Category {
        return Category.valueOf(name)
    }

    private fun LocalImage.toDomainImage(): Image {
        return Image(
            id = id,
            width = width,
            height = height,
        )
    }

    private fun List<LocalImage>.toDomainImages(): List<Image> {
        return map { it.toDomainImage() }
    }

    private fun List<LocalReleaseDate>.toDomainReleaseDates(): List<ReleaseDate> {
        return map {
            ReleaseDate(
                date = it.date,
                year = it.year,
                category = ReleaseDateCategory.valueOf(it.category.name),
            )
        }
    }

    private fun List<LocalAgeRating>.toDomainAgeRatings(): List<AgeRating> {
        return map {
            AgeRating(
                category = AgeRatingCategory.valueOf(it.category.name),
                type = AgeRatingType.valueOf(it.type.name),
            )
        }
    }

    private fun List<LocalVideo>.toDomainVideos(): List<Video> {
        return map {
            Video(
                id = it.id,
                name = it.name,
            )
        }
    }

    private fun List<LocalGenre>.toDomainGenres(): List<Genre> {
        return map {
            Genre(
                name = it.name,
            )
        }
    }

    private fun List<LocalPlatform>.toDomainPlatforms(): List<Platform> {
        return map {
            Platform(
                abbreviation = it.abbreviation,
                name = it.name,
            )
        }
    }

    private fun List<LocalPlayerPerspective>.toDomainPlayerPerspectives(): List<PlayerPerspective> {
        return map {
            PlayerPerspective(
                name = it.name,
            )
        }
    }

    private fun List<LocalTheme>.toDomainThemes(): List<Theme> {
        return map {
            Theme(
                name = it.name,
            )
        }
    }

    private fun List<LocalMode>.toDomainModes(): List<Mode> {
        return map {
            Mode(
                name = it.name,
            )
        }
    }

    private fun List<LocalKeyword>.toDomainKeywords(): List<Keyword> {
        return map {
            Keyword(
                name = it.name,
            )
        }
    }

    private fun List<LocalInvolvedCompany>.toDomainInvolvedCompanies(): List<InvolvedCompany> {
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

    private fun LocalCompany.toDomainCompany(): Company {
        return Company(
            id = id,
            name = name,
            websiteUrl = websiteUrl,
            logo = logo?.toDomainImage(),
            developedGames = developedGames,
        )
    }

    private fun List<LocalWebsite>.toDomainWebsites(): List<Website> {
        return map {
            Website(
                id = it.id,
                url = it.url,
                category = WebsiteCategory.valueOf(it.category.name),
            )
        }
    }

    fun mapToDatabaseGame(domainGame: Game): LocalGame {
        return LocalGame(
            id = domainGame.id,
            hypeCount = domainGame.hypeCount,
            releaseDate = domainGame.releaseDate,
            criticsRating = domainGame.criticsRating,
            usersRating = domainGame.usersRating,
            totalRating = domainGame.totalRating,
            name = domainGame.name,
            summary = domainGame.summary,
            storyline = domainGame.storyline,
            category = domainGame.category.toDbCategory(),
            cover = domainGame.cover?.toDbImage(),
            releaseDates = domainGame.releaseDates.toDbReleaseDates(),
            ageRatings = domainGame.ageRatings.toDbAgeRatings(),
            videos = domainGame.videos.toDbVideos(),
            artworks = domainGame.artworks.toDbImages(),
            screenshots = domainGame.screenshots.toDbImages(),
            genres = domainGame.genres.toDbGenres(),
            platforms = domainGame.platforms.toDbPlatforms(),
            playerPerspectives = domainGame.playerPerspectives.toDbPlayerPerspectives(),
            themes = domainGame.themes.toDbThemes(),
            modes = domainGame.modes.toDbModes(),
            keywords = domainGame.keywords.toDbKeywords(),
            involvedCompanies = domainGame.involvedCompanies.toDatabaseInvolvedCompanies(),
            websites = domainGame.websites.toDbWebsites(),
            similarGames = domainGame.similarGames,
        )
    }

    private fun Category.toDbCategory(): LocalCategory {
        return LocalCategory.valueOf(name)
    }

    private fun Image.toDbImage(): LocalImage {
        return LocalImage(
            id = id,
            width = width,
            height = height,
        )
    }

    private fun List<Image>.toDbImages(): List<LocalImage> {
        return map { it.toDbImage() }
    }

    private fun List<ReleaseDate>.toDbReleaseDates(): List<LocalReleaseDate> {
        return map {
            LocalReleaseDate(
                date = it.date,
                year = it.year,
                category = LocalReleaseDateCategory.valueOf(it.category.name),
            )
        }
    }

    private fun List<AgeRating>.toDbAgeRatings(): List<LocalAgeRating> {
        return map {
            LocalAgeRating(
                category = LocalAgeRatingCategory.valueOf(it.category.name),
                type = LocalAgeRatingType.valueOf(it.type.name),
            )
        }
    }

    private fun List<Video>.toDbVideos(): List<LocalVideo> {
        return map {
            LocalVideo(
                id = it.id,
                name = it.name,
            )
        }
    }

    private fun List<Genre>.toDbGenres(): List<LocalGenre> {
        return map {
            LocalGenre(
                name = it.name,
            )
        }
    }

    private fun List<Platform>.toDbPlatforms(): List<LocalPlatform> {
        return map {
            LocalPlatform(
                abbreviation = it.abbreviation,
                name = it.name,
            )
        }
    }

    private fun List<PlayerPerspective>.toDbPlayerPerspectives(): List<LocalPlayerPerspective> {
        return map {
            LocalPlayerPerspective(
                name = it.name,
            )
        }
    }

    private fun List<Theme>.toDbThemes(): List<LocalTheme> {
        return map {
            LocalTheme(
                name = it.name,
            )
        }
    }

    private fun List<Mode>.toDbModes(): List<LocalMode> {
        return map {
            LocalMode(
                name = it.name,
            )
        }
    }

    private fun List<Keyword>.toDbKeywords(): List<LocalKeyword> {
        return map {
            LocalKeyword(
                name = it.name,
            )
        }
    }

    private fun List<InvolvedCompany>.toDatabaseInvolvedCompanies(): List<LocalInvolvedCompany> {
        return map {
            LocalInvolvedCompany(
                company = it.company.toDbCompany(),
                isDeveloper = it.isDeveloper,
                isPublisher = it.isPublisher,
                isPorter = it.isPorter,
                isSupporting = it.isSupporting,
            )
        }
    }

    private fun Company.toDbCompany(): LocalCompany {
        return LocalCompany(
            id = id,
            name = name,
            websiteUrl = websiteUrl,
            logo = logo?.toDbImage(),
            developedGames = developedGames,
        )
    }

    private fun List<Website>.toDbWebsites(): List<LocalWebsite> {
        return map {
            LocalWebsite(
                id = it.id,
                url = it.url,
                category = LocalWebsiteCategory.valueOf(it.category.name),
            )
        }
    }
}

internal fun DbGameMapper.mapToDomainGames(localGames: List<LocalGame>): List<Game> {
    return localGames.map(::mapToDomainGame)
}

internal fun DbGameMapper.mapToDatabaseGames(domainGames: List<Game>): List<LocalGame> {
    return domainGames.map(::mapToDatabaseGame)
}
