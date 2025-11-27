package com.paulrybitskyi.gamedge.igdb.apicalypse.serialization

import com.paulrybitskyi.gamedge.igdb.apicalypse.Constants
import com.paulrybitskyi.gamedge.igdb.apicalypse.serialization.annotations.Apicalypse
import com.paulrybitskyi.gamedge.igdb.apicalypse.serialization.annotations.ApicalypseClass
import com.paulrybitskyi.gamedge.igdb.apicalypse.serialization.fieldserializers.FieldSerializer
import com.paulrybitskyi.gamedge.igdb.apicalypse.serialization.fieldserializers.FieldSerializerFactory
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType

internal class ApicalypseSerializerImpl : ApicalypseSerializer {

    override fun serialize(clazz: Class<*>): String {
        checkIfMarkerAnnotationIsPresent(clazz)

        return clazz
            .fieldSerializers(fieldChain = mutableListOf())
            .joinToString(
                separator = Constants.FIELD_SEPARATOR,
                transform = FieldSerializer::serialize,
            )
    }

    private fun checkIfMarkerAnnotationIsPresent(clazz: Class<*>) {
        if (!clazz.isAnnotationPresent(ApicalypseClass::class.java)) {
            throw IllegalArgumentException(
                "The provided class, \"${clazz.simpleName}\', does not have the " +
                        "\"${ApicalypseClass::class.simpleName}\" annotation present.",
            )
        }
    }

    private fun Class<*>.fieldSerializers(fieldChain: MutableList<String>): List<FieldSerializer> {
        if (!shouldBeSerialized()) return listOf()

        return buildList {
            for (field in declaredFields) {
                field.serializer(fieldChain)?.let(::add)
            }
        }
    }

    private fun Class<*>.shouldBeSerialized(): Boolean {
        return isAnnotationPresent(ApicalypseClass::class.java)
    }

    private fun Field.serializer(fieldChain: MutableList<String>): FieldSerializer? {
        if (!shouldBeSerialized()) return null

        val apicalypseAnnotation = checkNotNull(getAnnotation(Apicalypse::class.java))
        val fieldName = apicalypseAnnotation.name

        checkIfFieldNameIsValid(this, fieldName)

        fieldChain.add(fieldName)

        val childSerializers = childSerializers(fieldChain)
        val fieldSerializer = FieldSerializerFactory.create(
            fieldChain = fieldChain.toList(),
            children = childSerializers,
        )

        fieldChain.remove(fieldName)

        return fieldSerializer
    }

    private fun Field.shouldBeSerialized(): Boolean {
        return isAnnotationPresent(Apicalypse::class.java)
    }

    private fun checkIfFieldNameIsValid(field: Field, name: String) {
        if (name.isBlank()) {
            throw IllegalArgumentException(
                "The field \"${field.name}\" of the class \"${field.declaringClass.simpleName}\" " +
                        "is annotated with an invalid name \"$name\".",
            )
        }
    }

    private fun Field.childSerializers(fieldChain: MutableList<String>): List<FieldSerializer> {
        return when {
            !type.isGeneric() -> type.fieldSerializers(fieldChain)
            type.isCollectionGenericType() -> {
                val parameterizedType = (genericType as ParameterizedType)
                val typeArgument = parameterizedType.actualTypeArguments.first()
                val typeClass = (typeArgument as Class<*>)

                typeClass.fieldSerializers(fieldChain)
            }

            // All other types are unsupported for now
            else -> error(
                "Serialization for a class \"${type.simpleName}\" is unsupported at the moment.",
            )
        }
    }

    private fun Class<*>.isGeneric(): Boolean {
        return typeParameters.isNotEmpty()
    }

    private fun Class<*>.isCollectionGenericType(): Boolean {
        return Collection::class.java.isAssignableFrom(this)
    }
}
