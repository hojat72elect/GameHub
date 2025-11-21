
package com.paulrybitskyi.gamedge.common.data.auth.file

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.common.data.DOMAIN_OAUTH_CREDENTIALS
import com.paulrybitskyi.gamedge.common.data.auth.datastores.file.AUTH_TOKEN_TTL_DEDUCTION
import com.paulrybitskyi.gamedge.common.data.auth.datastores.file.AuthExpiryTimeCalculator
import com.paulrybitskyi.gamedge.core.providers.TimestampProvider
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

private const val CURRENT_TIMESTAMP = 10_000L

internal class AuthExpiryTimeCalculatorTest {

    @MockK private lateinit var timestampProvider: TimestampProvider

    private lateinit var SUT: AuthExpiryTimeCalculator

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        SUT = AuthExpiryTimeCalculator(timestampProvider)

        every { timestampProvider.getUnixTimestamp(any()) } returns CURRENT_TIMESTAMP
    }

    @Test
    fun `Calculates expiry time successfully`() {
        val credentials = DOMAIN_OAUTH_CREDENTIALS
        val expiryTime = SUT.calculateExpiryTime(credentials)
        val expected = (CURRENT_TIMESTAMP + TimeUnit.SECONDS.toMillis(credentials.tokenTtl) - AUTH_TOKEN_TTL_DEDUCTION)

        assertThat(expiryTime).isEqualTo(expected)
    }
}
