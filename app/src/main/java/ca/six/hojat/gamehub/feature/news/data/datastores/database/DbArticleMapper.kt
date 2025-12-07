package ca.six.hojat.gamehub.feature.news.data.datastores.database

import ca.six.hojat.gamehub.database.articles.entities.DbArticle
import ca.six.hojat.gamehub.database.articles.entities.DbImageType
import ca.six.hojat.gamehub.feature.news.domain.entities.Article
import ca.six.hojat.gamehub.feature.news.domain.entities.ImageType
import javax.inject.Inject

internal class DbArticleMapper @Inject constructor() {

    fun mapToDatabaseArticle(dataArticle: Article) = DbArticle(
        id = dataArticle.id,
        title = dataArticle.title,
        lede = dataArticle.lede,
        imageUrls = dataArticle.imageUrls.toDatabaseImageUrls(),
        publicationDate = dataArticle.publicationDate,
        siteDetailUrl = dataArticle.siteDetailUrl,
    )

    private fun Map<ImageType, String>.toDatabaseImageUrls(): Map<DbImageType, String> {
        return mapKeys {
            DbImageType.valueOf(it.key.name)
        }
    }

    fun mapToDomainArticle(databaseArticle: DbArticle) = Article(
        id = databaseArticle.id,
        title = databaseArticle.title,
        lede = databaseArticle.lede,
        imageUrls = databaseArticle.imageUrls.toDomainImageUrls(),
        publicationDate = databaseArticle.publicationDate,
        siteDetailUrl = databaseArticle.siteDetailUrl,
    )

    private fun Map<DbImageType, String>.toDomainImageUrls(): Map<ImageType, String> {
        return mapKeys {
            ImageType.valueOf(it.key.name)
        }
    }

    fun mapToDatabaseArticles(dataArticles: List<Article>) = dataArticles.map(::mapToDatabaseArticle)
    fun mapToDomainArticles(databaseArticles: List<DbArticle>) = databaseArticles.map(::mapToDomainArticle)
}