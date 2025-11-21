
package com.paulrybitskyi.gamedge.igdb.apicalypse.serialization.fieldserializers

import com.paulrybitskyi.gamedge.igdb.apicalypse.Constants

internal class SingleFieldSerializerImpl(
    private val fieldChain: List<String>,
) : FieldSerializer {

    override fun serialize(): String {
        return fieldChain.joinToString(Constants.HIERARCHICAL_FIELD_SEPARATOR)
    }
}
