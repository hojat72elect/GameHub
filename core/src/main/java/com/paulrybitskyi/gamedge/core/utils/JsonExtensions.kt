
package com.paulrybitskyi.gamedge.core.utils

import kotlinx.serialization.json.Json

inline fun <reified T> Json.decodeFromStringOrNull(json: String): T? {
    return try {
        decodeFromString(json)
    } catch (ignore: Throwable) {
        null
    }
}
