package ca.six.hojat.gamehub.igdb.apicalypse.serialization.fieldserializers

import ca.six.hojat.gamehub.igdb.apicalypse.Constants

internal class CompositeFieldSerializer(
    private val children: List<FieldSerializer>,
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
