package ca.six.hojat.gamehub.feature.news.data.datastores.gamespot

import ca.six.hojat.gamehub.feature.news.domain.DomainArticle
import ca.six.hojat.gamehub.feature.news.domain.DomainImageType
import ca.six.hojat.gamehub.gamespot.api.articles.entities.ApiArticle
import ca.six.hojat.gamehub.gamespot.api.articles.entities.ApiImageType
import javax.inject.Inject

internal class GamespotArticleMapper @Inject constructor(
    private val publicationDateMapper: ArticlePublicationDateMapper,
) {

    fun mapToDomainArticle(apiArticle: ApiArticle): DomainArticle {
        return DomainArticle(
            id = apiArticle.id,
            title = apiArticle.title,
            lede = apiArticle.lede,
            imageUrls = apiArticle.imageUrls.toDataImageUrls(),
            publicationDate = publicationDateMapper.mapToTimestamp(apiArticle.publicationDate),
            siteDetailUrl = apiArticle.siteDetailUrl,
        )
    }

    private fun Map<ApiImageType, String>.toDataImageUrls(): Map<DomainImageType, String> {
        return mapKeys {
            DomainImageType.valueOf(it.key.name)
        }
    }
}

internal fun GamespotArticleMapper.mapToDomainArticles(apiArticles: List<ApiArticle>): List<DomainArticle> {
    return apiArticles.map(::mapToDomainArticle)
}
