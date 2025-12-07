package ca.six.hojat.gamehub.feature.news.data.datastores.gamespot

import ca.six.hojat.gamehub.feature.news.domain.entities.Article
import ca.six.hojat.gamehub.feature.news.domain.entities.ImageType
import ca.six.hojat.gamehub.gamespot.api.articles.entities.ApiArticle
import ca.six.hojat.gamehub.gamespot.api.articles.entities.ApiImageType
import javax.inject.Inject

internal class GamespotArticleMapper @Inject constructor(private val publicationDateMapper: ArticlePublicationDateMapper) {

    fun mapToDomainArticle(apiArticle: ApiArticle) = Article(
        id = apiArticle.id,
        title = apiArticle.title,
        lede = apiArticle.lede,
        imageUrls = apiArticle.imageUrls.toDataImageUrls(),
        publicationDate = publicationDateMapper.mapToTimestamp(apiArticle.publicationDate),
        siteDetailUrl = apiArticle.siteDetailUrl,
    )

    private fun Map<ApiImageType, String>.toDataImageUrls(): Map<ImageType, String> {
        return mapKeys {
            ImageType.valueOf(it.key.name)
        }
    }

    fun mapToDomainArticles(apiArticles: List<ApiArticle>) = apiArticles.map(::mapToDomainArticle)
}
