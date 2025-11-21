package com.paulrybitskyi.gamedge.feature.news.data.datastores.database

import com.paulrybitskyi.gamedge.database.articles.DatabaseArticle
import com.paulrybitskyi.gamedge.database.articles.DatabaseImageType
import com.paulrybitskyi.gamedge.feature.news.domain.DomainArticle
import com.paulrybitskyi.gamedge.feature.news.domain.DomainImageType
import javax.inject.Inject

internal class DbArticleMapper @Inject constructor() {

    fun mapToDatabaseArticle(dataArticle: DomainArticle): DatabaseArticle {
        return DatabaseArticle(
            id = dataArticle.id,
            title = dataArticle.title,
            lede = dataArticle.lede,
            imageUrls = dataArticle.imageUrls.toDatabaseImageUrls(),
            publicationDate = dataArticle.publicationDate,
            siteDetailUrl = dataArticle.siteDetailUrl,
        )
    }

    private fun Map<DomainImageType, String>.toDatabaseImageUrls(): Map<DatabaseImageType, String> {
        return mapKeys {
            DatabaseImageType.valueOf(it.key.name)
        }
    }

    fun mapToDomainArticle(databaseArticle: DatabaseArticle): DomainArticle {
        return DomainArticle(
            id = databaseArticle.id,
            title = databaseArticle.title,
            lede = databaseArticle.lede,
            imageUrls = databaseArticle.imageUrls.toDomainImageUrls(),
            publicationDate = databaseArticle.publicationDate,
            siteDetailUrl = databaseArticle.siteDetailUrl,
        )
    }

    private fun Map<DatabaseImageType, String>.toDomainImageUrls(): Map<DomainImageType, String> {
        return mapKeys {
            DomainImageType.valueOf(it.key.name)
        }
    }
}

internal fun DbArticleMapper.mapToDatabaseArticles(dataArticles: List<DomainArticle>): List<DatabaseArticle> {
    return dataArticles.map(::mapToDatabaseArticle)
}

internal fun DbArticleMapper.mapToDomainArticles(databaseArticles: List<DatabaseArticle>): List<DomainArticle> {
    return databaseArticles.map(::mapToDomainArticle)
}
