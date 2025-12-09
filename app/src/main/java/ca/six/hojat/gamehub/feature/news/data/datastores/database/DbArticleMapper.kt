package ca.six.hojat.gamehub.feature.news.data.datastores.database

import ca.six.hojat.gamehub.feature.news.domain.entities.Article
import ca.six.hojat.gamehub.feature.news.domain.entities.ImageType
import ca.six.hojat.gamehub.shared.data.local.news_articles.entities.LocalImageType
import ca.six.hojat.gamehub.shared.data.local.news_articles.entities.LocalNewsArticle
import javax.inject.Inject

internal class DbArticleMapper @Inject constructor() {

    fun mapToDatabaseArticle(dataArticle: Article) = LocalNewsArticle(
        id = dataArticle.id,
        title = dataArticle.title,
        lede = dataArticle.lede,
        imageUrls = dataArticle.imageUrls.toDatabaseImageUrls(),
        publicationDate = dataArticle.publicationDate,
        siteDetailUrl = dataArticle.siteDetailUrl,
    )

    private fun Map<ImageType, String>.toDatabaseImageUrls(): Map<LocalImageType, String> {
        return mapKeys {
            LocalImageType.valueOf(it.key.name)
        }
    }

    fun mapToDomainArticle(databaseArticle: LocalNewsArticle) = Article(
        id = databaseArticle.id,
        title = databaseArticle.title,
        lede = databaseArticle.lede,
        imageUrls = databaseArticle.imageUrls.toDomainImageUrls(),
        publicationDate = databaseArticle.publicationDate,
        siteDetailUrl = databaseArticle.siteDetailUrl,
    )

    private fun Map<LocalImageType, String>.toDomainImageUrls(): Map<ImageType, String> {
        return mapKeys {
            ImageType.valueOf(it.key.name)
        }
    }

    fun mapToDatabaseArticles(dataArticles: List<Article>) = dataArticles.map(::mapToDatabaseArticle)
    fun mapToDomainArticles(databaseArticles: List<LocalNewsArticle>) = databaseArticles.map(::mapToDomainArticle)
}