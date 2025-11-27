package ca.six.hojat.gamehub.igdb.apicalypse.querybuilder.whereclause

import ca.six.hojat.gamehub.igdb.apicalypse.querybuilder.whereclause.conditions.ConditionBuilderFactory

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
