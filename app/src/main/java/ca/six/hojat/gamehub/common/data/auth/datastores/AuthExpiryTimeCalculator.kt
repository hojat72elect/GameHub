package ca.six.hojat.gamehub.common.data.auth.datastores

import ca.six.hojat.gamehub.common.domain.auth.entities.OauthCredentials
import ca.six.hojat.gamehub.core.providers.TimestampProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal val AUTH_TOKEN_TTL_DEDUCTION = TimeUnit.DAYS.toMillis(@Suppress("MagicNumber") 7)

internal class AuthExpiryTimeCalculator @Inject constructor(
    private val timestampProvider: TimestampProvider,
) {

    fun calculateExpiryTime(oauthCredentials: OauthCredentials): Long {
        val currentTimestamp = timestampProvider.getUnixTimestamp()
        val tokenTtlInMillis = TimeUnit.SECONDS.toMillis(oauthCredentials.tokenTtl)
        val expiryTime = (currentTimestamp + tokenTtlInMillis - AUTH_TOKEN_TTL_DEDUCTION)

        return expiryTime
    }
}
