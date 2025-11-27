package com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder

import com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause.WhereClauseBuilder
import com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause.WhereClauseBuilderFactory

internal class ApicalypseQueryBuilderImpl(
    private val whereClauseBuilderFactory: WhereClauseBuilderFactory,
) : ApicalypseQueryBuilder {

    private val queryBuilder = StringBuilder()

    override fun search(query: String) = apply {
        queryBuilder.append("search \"$query\";")
    }

    override fun select(fields: String) = apply {
        queryBuilder.append("fields $fields;")
    }

    override fun selectAll() = apply {
        queryBuilder.append("fields *;")
    }

    override fun exclude(fields: String) = apply {
        queryBuilder.append("exclude $fields;")
    }

    override fun where(builder: WhereClauseBuilder.() -> Unit) = apply {
        queryBuilder
            .append("where ")
            .append(whereClauseBuilderFactory.newBuilder().apply(builder).build())
            .append(";")
    }

    override fun offset(offset: Int) = apply {
        queryBuilder.append("offset $offset;")
    }

    override fun limit(limit: Int) = apply {
        queryBuilder.append("limit $limit;")
    }

    override fun sortAsc(field: String) = apply {
        queryBuilder.append("sort $field asc;")
    }

    override fun sortDesc(field: String) = apply {
        queryBuilder.append("sort $field desc;")
    }

    override fun build(): String {
        return queryBuilder.toString()
    }
}
