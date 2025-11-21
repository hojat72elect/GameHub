
package com.paulrybitskyi.gamedge.gamespot.api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Response<T : Any>(
    @SerialName(Schema.RESULTS)
    val results: List<T> = emptyList(),
) {

    object Schema {
        const val RESULTS = "results"
    }
}
