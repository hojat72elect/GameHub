package com.paulrybitskyi.gamedge.gamespot.api.common.serialization

import com.paulrybitskyi.hiltbinder.BindType
import java.lang.reflect.Field
import javax.inject.Inject

private const val FIELD_SEPARATOR = ","

internal interface GamespotFieldsSerializer {
    fun serializeFields(clazz: Class<*>): String
}

@BindType
internal class GamespotFieldsSerializerImpl @Inject constructor() : GamespotFieldsSerializer {

    override fun serializeFields(clazz: Class<*>): String {
        return clazz.getGamespotFieldNames().joinToString(FIELD_SEPARATOR)
    }

    private fun Class<*>.getGamespotFieldNames(): List<String> {
        return declaredFields
            .filter { it.hasGamespotAnnotation() }
            .map { it.getGamespotName() }
    }

    private fun Field.hasGamespotAnnotation(): Boolean {
        return isAnnotationPresent(Gamespot::class.java)
    }

    private fun Field.getGamespotName(): String {
        val annotation = checkNotNull(getAnnotation(Gamespot::class.java))
        val fieldName = annotation.value

        if (fieldName.isBlank()) {
            throw IllegalArgumentException(
                "The field \"${name}\" of the class \"${declaringClass.simpleName}\" " +
                        "is annotated with an invalid name \"$fieldName\"",
            )
        }

        return fieldName
    }
}
