package ca.six.hojat.gamehub.shared.data.local.authentication

import ca.six.hojat.gamehub.common.data.auth.datastores.AuthExpiryTimeCalculator
import ca.six.hojat.gamehub.common.domain.auth.entities.OauthCredentials
import ca.six.hojat.gamehub.shared.data.local.authentication.entities.LocalAuthenticationCredentials
import javax.inject.Inject

internal class LocalAuthenticationMapper @Inject constructor(
    private val authExpiryTimeCalculator: AuthExpiryTimeCalculator,
) {

    fun mapToDbOauthCredentials(oauthCredentials: OauthCredentials): LocalAuthenticationCredentials {
        return LocalAuthenticationCredentials(
            accessToken = oauthCredentials.accessToken,
            tokenType = oauthCredentials.tokenType,
            tokenTtl = oauthCredentials.tokenTtl,
            expirationTime = authExpiryTimeCalculator.calculateExpiryTime(oauthCredentials),
        )
    }

    fun mapToDomainOauthCredentials(oauthCredentials: LocalAuthenticationCredentials): OauthCredentials {
        return OauthCredentials(
            accessToken = oauthCredentials.accessToken,
            tokenType = oauthCredentials.tokenType,
            tokenTtl = oauthCredentials.tokenTtl,
        )
    }
}
