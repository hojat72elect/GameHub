
package com.paulrybitskyi.gamedge.gamespot.api.articles.entities

import com.paulrybitskyi.gamedge.gamespot.api.common.serialization.Gamespot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiArticle(
    @Gamespot(Schema.ID)
    @SerialName(Schema.ID)
    val id: Int = -1,
    @Gamespot(Schema.TITLE)
    @SerialName(Schema.TITLE)
    val title: String = "",
    @Gamespot(Schema.LEDE)
    @SerialName(Schema.LEDE)
    val lede: String = "",
    @Gamespot(Schema.IMAGE_URLS)
    @SerialName(Schema.IMAGE_URLS)
    val imageUrls: Map<ApiImageType, String> = mapOf(),
    @Gamespot(Schema.PUBLICATION_DATE)
    @SerialName(Schema.PUBLICATION_DATE)
    val publicationDate: String = "",
    @Gamespot(Schema.SITE_DETAIL_URL)
    @SerialName(Schema.SITE_DETAIL_URL)
    val siteDetailUrl: String = "",
) {

    object Schema {
        const val ID = "id"
        const val TITLE = "title"
        const val LEDE = "lede"
        const val IMAGE_URLS = "image"
        const val PUBLICATION_DATE = "publish_date"
        const val SITE_DETAIL_URL = "site_detail_url"
    }
}
