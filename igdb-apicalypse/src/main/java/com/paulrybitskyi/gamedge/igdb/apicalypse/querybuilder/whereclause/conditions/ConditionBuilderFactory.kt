
package com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause.conditions

import com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause.WhereClauseBuilderFactory

internal object ConditionBuilderFactory {

    fun newBuilder(conditionType: ConditionType): ConditionBuilder {
        return ConditionBuilderImpl(
            conditionType = conditionType,
            whereClauseBuilderFactory = WhereClauseBuilderFactory,
        )
    }
}
