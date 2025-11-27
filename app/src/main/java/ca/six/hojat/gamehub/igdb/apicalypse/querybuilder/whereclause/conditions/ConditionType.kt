package ca.six.hojat.gamehub.igdb.apicalypse.querybuilder.whereclause.conditions

internal enum class ConditionType(val separator: String) {
    AND(" & "),
    OR(" | "),
}
