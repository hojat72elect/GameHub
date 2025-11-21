
package com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause.conditions

internal enum class ConditionType(val separator: String) {
    AND(" & "),
    OR(" | "),
}
