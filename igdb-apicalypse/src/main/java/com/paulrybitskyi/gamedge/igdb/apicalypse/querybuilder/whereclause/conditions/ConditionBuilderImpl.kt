
package com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause.conditions

import com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause.Condition
import com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause.WhereClauseBuilderFactory

internal class ConditionBuilderImpl(
    private val conditionType: ConditionType,
    private val whereClauseBuilderFactory: WhereClauseBuilderFactory,
) : ConditionBuilder {

    private val conditionBuilder = StringBuilder()

    override fun condition(condition: Condition) = apply {
        conditionBuilder
            .append(conditionType.separator)
            .append(condition.buildCondition())
    }

    private fun Condition.buildCondition(): String {
        return whereClauseBuilderFactory.newBuilder().apply(this).build()
    }

    override fun build(): String {
        return conditionBuilder.toString()
    }
}
