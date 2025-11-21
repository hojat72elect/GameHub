
package com.paulrybitskyi.gamedge.igdb.api.auth.entities

enum class ApiAuthorizationType(val rawType: String) {
    BASIC("Basic"),
    BEARER("Bearer"),
    ;

    companion object {

        fun forRawType(rawType: String): ApiAuthorizationType {
            return entries.find { it.rawType == rawType }
                ?: throw IllegalArgumentException("No auth type with raw type = $rawType.")
        }
    }
}
