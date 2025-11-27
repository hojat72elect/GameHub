package ca.six.hojat.gamehub.igdb.apicalypse.querybuilder

import ca.six.hojat.gamehub.igdb.apicalypse.querybuilder.whereclause.WhereClauseBuilderFactory

object ApicalypseQueryBuilderFactory {

    fun create(): ApicalypseQueryBuilder {
        return ApicalypseQueryBuilderImpl(
            whereClauseBuilderFactory = WhereClauseBuilderFactory,
        )
    }
}
