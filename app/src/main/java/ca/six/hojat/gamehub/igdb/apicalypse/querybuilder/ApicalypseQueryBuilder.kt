package ca.six.hojat.gamehub.igdb.apicalypse.querybuilder

import ca.six.hojat.gamehub.igdb.apicalypse.querybuilder.whereclause.WhereClauseBuilder

interface ApicalypseQueryBuilder {
    fun search(query: String): ApicalypseQueryBuilder
    fun select(fields: String): ApicalypseQueryBuilder
    fun selectAll(): ApicalypseQueryBuilder
    fun exclude(fields: String): ApicalypseQueryBuilder
    fun where(builder: WhereClauseBuilder.() -> Unit): ApicalypseQueryBuilder
    fun offset(offset: Int): ApicalypseQueryBuilder
    fun limit(limit: Int): ApicalypseQueryBuilder
    fun sortAsc(field: String): ApicalypseQueryBuilder
    fun sortDesc(field: String): ApicalypseQueryBuilder
    fun build(): String
}
