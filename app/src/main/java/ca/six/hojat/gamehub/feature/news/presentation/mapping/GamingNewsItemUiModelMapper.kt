package ca.six.hojat.gamehub.feature.news.presentation.mapping

import ca.six.hojat.gamehub.core.formatters.ArticlePublicationDateFormatter
import ca.six.hojat.gamehub.feature.news.domain.entities.Article
import ca.six.hojat.gamehub.feature.news.domain.entities.ImageType
import ca.six.hojat.gamehub.feature.news.presentation.widgets.GamingNewsItemUiModel
import javax.inject.Inject

internal interface GamingNewsItemUiModelMapper {
    fun mapToUiModel(article: Article): GamingNewsItemUiModel
}

internal class GamingNewsItemUiModelMapperImpl @Inject constructor(
    private val publicationDateFormatter: ArticlePublicationDateFormatter,
) : GamingNewsItemUiModelMapper {

    override fun mapToUiModel(article: Article): GamingNewsItemUiModel {
        return GamingNewsItemUiModel(
            id = article.id,
            imageUrl = article.imageUrls[ImageType.ORIGINAL],
            title = article.title,
            lede = article.lede,
            publicationDate = article.formatPublicationDate(),
            siteDetailUrl = article.siteDetailUrl,
        )
    }

    private fun Article.formatPublicationDate(): String {
        return publicationDateFormatter.formatPublicationDate(publicationDate)
    }
}

internal fun GamingNewsItemUiModelMapper.mapToUiModels(
    articles: List<Article>,
): List<GamingNewsItemUiModel> {
    return articles.map(::mapToUiModel)
}
