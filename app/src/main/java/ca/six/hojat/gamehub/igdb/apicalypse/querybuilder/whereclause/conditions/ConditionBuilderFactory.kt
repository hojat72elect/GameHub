package ca.six.hojat.gamehub.igdb.apicalypse.querybuilder.whereclause.conditions

import ca.six.hojat.gamehub.igdb.apicalypse.querybuilder.whereclause.WhereClauseBuilderFactory

internal object ConditionBuilderFactory {

    fun newBuilder(conditionType: ConditionType): ConditionBuilder {
        return ConditionBuilderImpl(
            conditionType = conditionType,
            whereClauseBuilderFactory = WhereClauseBuilderFactory,
        )
    }
}
