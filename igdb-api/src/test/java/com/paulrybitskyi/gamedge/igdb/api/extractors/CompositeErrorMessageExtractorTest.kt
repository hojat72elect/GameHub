package com.paulrybitskyi.gamedge.igdb.api.extractors

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.igdb.api.common.errorextractors.CompositeErrorMessageExtractor
import com.paulrybitskyi.gamedge.igdb.api.common.errorextractors.IgdbErrorMessageExtractor
import com.paulrybitskyi.gamedge.igdb.api.common.errorextractors.TwitchErrorMessageExtractor
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

internal class CompositeErrorMessageExtractorTest {

    private lateinit var SUT: CompositeErrorMessageExtractor

    @Before
    fun setup() {
        SUT = CompositeErrorMessageExtractor(
            setOf(
                TwitchErrorMessageExtractor(Json),
                IgdbErrorMessageExtractor(Json),
            ),
        )
    }

    @Test
    fun `Extracts twitch error message from composite extractor successfully`() {
        val responseBody = """
            {
                "status":403,
                "message": "invalid client secret"
            }
        """.trimIndent()

        assertThat(SUT.extract(responseBody)).isEqualTo("invalid client secret")
    }

    @Test
    fun `Extracts igdb error message from composite extractor successfully`() {
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
    fun `Returns unknown error if composite extractor fails to extract message`() {
        val responseBody = """
            {
                "status":403,
                "error_message": "invalid client secret"
            }
        """.trimIndent()

        assertThat(SUT.extract(responseBody)).isEqualTo("Unknown Error: $responseBody")
    }
}
