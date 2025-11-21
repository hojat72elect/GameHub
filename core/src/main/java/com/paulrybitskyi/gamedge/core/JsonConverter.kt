
package com.paulrybitskyi.gamedge.core

import com.paulrybitskyi.gamedge.core.utils.decodeFromStringOrNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonConverter @Inject constructor(val json: Json) {

    inline fun <reified T> toJson(clazz: T): String {
        return this.json.encodeToString(clazz)
    }

    inline fun <reified T> fromJson(json: String): T? {
        return this.json.decodeFromStringOrNull(json)
    }
}
