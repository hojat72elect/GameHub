
package com.paulrybitskyi.gamedge.igdb.api.extractors

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.igdb.api.common.errorextractors.IgdbErrorMessageExtractor
import kotlinx.serialization.json.Json
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

internal class IgdbErrorMessageExtractorTest {

    private lateinit var SUT: IgdbErrorMessageExtractor

    @Before
    fun setup() {
        SUT = IgdbErrorMessageExtractor(Json)
    }

    @Test
    fun `Extracts igdb error message successfully`() {
        val responseBody = """
            [
              {
                "title": "Syntax Error",
                "status": 400,
                "cause": "Missing `;` at end of query"
              }
            ]
        """.trimIndent()

        assertThat(SUT.extract(responseBody)).isEqualTo("Syntax Error")
    }

    @Test
    fun `Throws exception when igdb response body is not json`() {
        assertThrows(IllegalStateException::class.java) {
            SUT.extract("hello there")
        }
    }

    @Test
    fun `Throws exception when igdb response body does not have message field`() {
        val responseBody = """
            [
              {
                "cause": "Syntax Error",
                "status": 400
              }
            ]
        """.trimIndent()

        assertThrows(IllegalStateException::class.java) {
            SUT.extract(responseBody)
        }
    }
}
