
package com.paulrybitskyi.gamedge.gamespot.api

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.gamespot.api.common.serialization.Gamespot
import com.paulrybitskyi.gamedge.gamespot.api.common.serialization.GamespotFieldsSerializer
import com.paulrybitskyi.gamedge.gamespot.api.common.serialization.GamespotFieldsSerializerImpl
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

internal class GamespotFieldsSerializerImplTest {

    private lateinit var SUT: GamespotFieldsSerializer

    @Before
    fun setup() {
        SUT = GamespotFieldsSerializerImpl()
    }

    @Test
    fun `Serializes simple entity successfully`() {
        data class Entity(
            @Gamespot("field1")
            val field1: Int,
            @Gamespot("field2")
            val field2: String,
            @Gamespot("field3")
            val field3: Double,
            @Gamespot("field4")
            val field4: Float,
            @Gamespot("field5")
            val field5: String,
            @Gamespot("field6")
            val field6: Float,
        )

        assertThat(SUT.serializeFields(Entity::class.java))
            .isEqualTo("field1,field2,field3,field4,field5,field6")
    }

    @Test
    fun `Serializes entity with no annotated fields successfully`() {
        data class Entity(
            val field1: Int,
            val field2: String,
            val field3: Double,
        )

        assertThat(SUT.serializeFields(Entity::class.java)).isEmpty()
    }

    @Test
    fun `Throws exception if name of field is empty`() {
        data class Entity(
            @Gamespot("")
            val field1: Int,
        )

        assertThrows(IllegalArgumentException::class.java) {
            SUT.serializeFields(Entity::class.java)
        }
    }

    @Test
    fun `Throws exception if name of field is blank`() {
        data class Entity(
            @Gamespot("   ")
            val field1: Int,
        )

        assertThrows(IllegalArgumentException::class.java) {
            SUT.serializeFields(Entity::class.java)
        }
    }
}
