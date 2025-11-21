
package com.paulrybitskyi.gamedge.igdb.api.extractors

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.igdb.api.common.errorextractors.TwitchErrorMessageExtractor
import kotlinx.serialization.json.Json
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

internal class TwitchErrorMessageExtractorTest {

    private lateinit var SUT: TwitchErrorMessageExtractor

    @Before
    fun setup() {
        SUT = TwitchErrorMessageExtractor(Json)
    }

    @Test
    fun `Extracts twitch error message successfully`() {
        val responseBody = """
            {
                "status":403,
                "message": "invalid client secret"
            }
        """.trimIndent()

        assertThat(SUT.extract(responseBody)).isEqualTo("invalid client secret")
    }

    @Test
    fun `Throws exception when twitch response body is not json`() {
        assertThrows(IllegalStateException::class.java) {
            SUT.extract("hello there")
        }
    }

    @Test
    fun `Throws exception when twitch response body does not have message field`() {
        val responseBody = """
            {
                "status":403,
                "error": "invalid client secret"
            }
        """.trimIndent()

        assertThrows(IllegalStateException::class.java) {
            SUT.extract(responseBody)
        }
    }
}
