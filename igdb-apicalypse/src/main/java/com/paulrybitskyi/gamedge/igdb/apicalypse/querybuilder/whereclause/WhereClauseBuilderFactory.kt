
package com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause

import com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause.conditions.ConditionBuilderFactory

internal object WhereClauseBuilderFactory {

    fun newBuilder(): WhereClauseBuilder {
        return WhereClauseBuilderImpl(
            conditionBuilderFactory = createConditionBuilderFactory(),
        )
    }

    private fun createConditionBuilderFactory(): ConditionBuilderFactory {
        return ConditionBuilderFactory
    }
}
