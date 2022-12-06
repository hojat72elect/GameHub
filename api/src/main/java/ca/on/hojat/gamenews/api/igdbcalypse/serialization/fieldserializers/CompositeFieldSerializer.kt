package ca.on.hojat.gamenews.api.igdbcalypse.serialization.fieldserializers

import ca.on.hojat.gamenews.api.igdbcalypse.Constants

internal class CompositeFieldSerializer(
    private val children: List<FieldSerializer>
) : FieldSerializer {

    override fun serialize(): String {
        return buildList {
            for (child in children) {
                add(child.serialize())
            }
        }
            .joinToString(Constants.FIELD_SEPARATOR)
    }
}