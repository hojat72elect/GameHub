
package com.paulrybitskyi.gamedge.database.articles.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = DbArticle.Schema.TABLE_NAME,
    primaryKeys = [DbArticle.Schema.ID],
    indices = [
        Index(DbArticle.Schema.PUBLICATION_DATE),
    ],
)
data class DbArticle(
    @ColumnInfo(name = Schema.ID) val id: Int,
    @ColumnInfo(name = Schema.TITLE) val title: String,
    @ColumnInfo(name = Schema.LEDE) val lede: String,
    @ColumnInfo(name = Schema.IMAGE_URLS) val imageUrls: Map<DbImageType, String>,
    @ColumnInfo(name = Schema.PUBLICATION_DATE) val publicationDate: Long,
    @ColumnInfo(name = Schema.SITE_DETAIL_URL) val siteDetailUrl: String,
) {

    object Schema {
        const val TABLE_NAME = "articles"
        const val ID = "id"
        const val TITLE = "title"
        const val LEDE = "lede"
        const val IMAGE_URLS = "image_urls"
        const val PUBLICATION_DATE = "publication_date"
        const val SITE_DETAIL_URL = "site_detail_url"
    }
}
