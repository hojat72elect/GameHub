package com.paulrybitskyi.gamedge.gamespot.api

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.gamespot.api.common.GamespotErrorMessageExtractor
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

internal class GamespotErrorMessageExtractorTest {

    private lateinit var SUT: GamespotErrorMessageExtractor

    @Before
    fun setup() {
        SUT = GamespotErrorMessageExtractor(Json)
    }

    @Test
    fun `Extracts error message successfully`() {
        val responseBody = """
            {
              "error": "Invalid API Key",
              "limit": 0,
              "offset": 0,
              "number_of_page_results": 0,
              "number_of_total_results": 0,
              "status_code": 100,
              "results": []
            }
        """.trimIndent()

        assertThat(SUT.extract(responseBody)).isEqualTo("Invalid API Key")
    }

    @Test
    fun `Returns unknown error's message when response body is not json`() {
        assertThat(SUT.extract("hello there")).isNotEmpty()
    }

    @Test
    fun `Returns unknown error's message when response body does not have message field`() {
        val responseBody = """
            {
              "limit": 0,
              "offset": 0,
              "number_of_page_results": 0,
              "number_of_total_results": 0,
              "status_code": 100,
              "results": []
            }
        """.trimIndent()

        assertThat(SUT.extract(responseBody)).isNotEmpty()
    }
}
