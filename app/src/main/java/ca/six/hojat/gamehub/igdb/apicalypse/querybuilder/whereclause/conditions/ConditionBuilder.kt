package ca.six.hojat.gamehub.igdb.apicalypse.querybuilder.whereclause.conditions

import ca.six.hojat.gamehub.igdb.apicalypse.querybuilder.whereclause.Condition

internal interface ConditionBuilder {
    fun condition(condition: Condition): ConditionBuilder
    fun build(): String
}
