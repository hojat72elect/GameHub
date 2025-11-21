
package com.paulrybitskyi.gamedge.igdb.api

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.igdb.api.auth.Authorizer
import com.paulrybitskyi.gamedge.igdb.api.auth.entities.ApiAuthorizationType
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class AuthorizerTest {

    private companion object {
        const val ACCESS_TOKEN = "access_token"
    }

    private lateinit var SUT: Authorizer

    @Before
    fun setup() {
        SUT = Authorizer()
    }

    @Test
    fun `Builds basic authorization header successfully`() {
        runTest {
            val expectedHeader = "Basic $ACCESS_TOKEN"
            val actualHeader = SUT.buildAuthorizationHeader(
                type = ApiAuthorizationType.BASIC,
                token = ACCESS_TOKEN,
            )

            assertThat(actualHeader).isEqualTo(expectedHeader)
        }
    }

    @Test
    fun `Builds bearer authorization header successfully`() {
        runTest {
            val expectedHeader = "Bearer $ACCESS_TOKEN"
            val actualHeader = SUT.buildAuthorizationHeader(
                type = ApiAuthorizationType.BEARER,
                token = ACCESS_TOKEN,
            )

            assertThat(actualHeader).isEqualTo(expectedHeader)
        }
    }
}
