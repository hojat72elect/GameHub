package com.paulrybitskyi.gamedge.igdb.api

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.igdb.api.auth.AuthHeaderParser
import com.paulrybitskyi.gamedge.igdb.api.auth.entities.ApiAuthorizationType
import org.junit.Before
import org.junit.Test

internal class AuthHeaderParserTest {

    private companion object {
        const val TOKEN = "token"
    }

    private lateinit var SUT: AuthHeaderParser

    @Before
    fun setup() {
        SUT = AuthHeaderParser()
    }

    @Test
    fun `Returns null when header string is empty`() {
        assertThat(SUT.parse("")).isNull()
    }

    @Test
    fun `Returns result with basic auth type`() {
        val expectedAuthType = ApiAuthorizationType.BASIC
        val actualResult = SUT.parse("Basic $TOKEN")

        assertThat(actualResult).isNotNull()
        assertThat(actualResult!!.type).isEqualTo(expectedAuthType)
        assertThat(actualResult.token).isEqualTo(TOKEN)
    }

    @Test
    fun `Returns result with bearer auth type`() {
        val expectedAuthType = ApiAuthorizationType.BEARER
        val actualResult = SUT.parse("Bearer $TOKEN")

        assertThat(actualResult).isNotNull()
        assertThat(actualResult!!.type).isEqualTo(expectedAuthType)
        assertThat(actualResult.token).isEqualTo(TOKEN)
    }
}
