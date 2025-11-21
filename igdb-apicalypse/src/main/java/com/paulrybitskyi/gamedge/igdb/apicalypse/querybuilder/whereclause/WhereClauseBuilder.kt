package com.paulrybitskyi.gamedge.igdb.apicalypse.querybuilder.whereclause

interface WhereClauseBuilder {

    val String.isTrue: WhereClauseBuilder
    val String.isFalse: WhereClauseBuilder
    val String.isNull: WhereClauseBuilder
    val String.isNotNull: WhereClauseBuilder

    fun String.isEqual(value: String): WhereClauseBuilder
    fun String.isNotEqual(value: String): WhereClauseBuilder
    fun String.isLargerThan(value: String): WhereClauseBuilder
    fun String.isLargerThanOrEqualTo(value: String): WhereClauseBuilder
    fun String.isSmallerThan(value: String): WhereClauseBuilder
    fun String.isSmallerThanOrEqualTo(value: String): WhereClauseBuilder
    fun String.containsAllOf(values: List<String>): WhereClauseBuilder
    fun String.doesNotContainAllOf(values: List<String>): WhereClauseBuilder
    fun String.containsAnyOf(values: List<String>): WhereClauseBuilder
    fun String.doesNotContainAnyOf(values: List<String>): WhereClauseBuilder
    fun String.containsExclusivelyAllOf(values: List<String>): WhereClauseBuilder

    infix fun and(condition: Condition): WhereClauseBuilder
    infix fun or(condition: Condition): WhereClauseBuilder

    fun build(): String
}
