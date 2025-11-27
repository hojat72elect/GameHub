package ca.six.hojat.gamehub.gamespot.api.common

import ca.six.hojat.gamehub.gamespot.api.articles.entities.ApiArticle
import ca.six.hojat.gamehub.gamespot.api.common.serialization.GamespotFieldsSerializer

internal interface GamespotQueryParamsFactory {

    fun createArticlesQueryParams(
        action: MutableMap<String, String>.() -> Unit,
    ): Map<String, String>
}

internal class GamespotQueryParamsFactoryImpl(
    private val gamespotFieldsSerializer: GamespotFieldsSerializer,
    private val apiKey: String,
) : GamespotQueryParamsFactory {

    private companion object {
        private const val ARTICLE_FIELD_CATEGORIES = "categories"
        private const val ARTICLE_CATEGORY_ID_GAMES = 18

        private const val COMPLEX_QUERY_VALUE_TEMPLATE = "%s:%s"
    }

    private val articleEntityFields by lazy {
        gamespotFieldsSerializer.serializeFields(ApiArticle::class.java)
    }

    override fun createArticlesQueryParams(
        action: MutableMap<String, String>.() -> Unit,
    ): Map<String, String> {
        return createGeneralQueryParams {
            put(QUERY_PARAM_FIELDS, articleEntityFields)
            put(
                QUERY_PARAM_FILTER,
                createFilterQueryParamValue(ARTICLE_FIELD_CATEGORIES, ARTICLE_CATEGORY_ID_GAMES),
            )
            put(
                QUERY_PARAM_SORT,
                createSortQueryParamValue(ApiArticle.Schema.PUBLICATION_DATE, SortOrder.DESC),
            )

            action()
        }
    }

    private fun createFilterQueryParamValue(field: String, value: Any): String {
        return String.format(COMPLEX_QUERY_VALUE_TEMPLATE, field, value)
    }

    private fun createSortQueryParamValue(field: String, order: SortOrder): String {
        return String.format(COMPLEX_QUERY_VALUE_TEMPLATE, field, order.rawOrder)
    }

    private fun createGeneralQueryParams(
        action: MutableMap<String, String>.() -> Unit,
    ): Map<String, String> {
        return buildMap {
            put(QUERY_PARAM_API_KEY, apiKey)
            put(QUERY_PARAM_FORMAT, ResponseFormat.JSON.rawFormat)

            action()
        }
    }
}
