
package com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause.conditions

import com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause.Condition

internal interface ConditionBuilder {
    fun condition(condition: Condition): ConditionBuilder
    fun build(): String
}
