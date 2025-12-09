package ca.six.hojat.gamehub.shared.data.local.auth

import ca.six.hojat.gamehub.common.data.auth.datastores.AuthExpiryTimeCalculator
import ca.six.hojat.gamehub.common.domain.auth.entities.OauthCredentials
import ca.six.hojat.gamehub.shared.data.local.auth.entities.DbOauthCredentials
import javax.inject.Inject

internal class DbAuthMapper @Inject constructor(
    private val authExpiryTimeCalculator: AuthExpiryTimeCalculator,
) {

    fun mapToDbOauthCredentials(oauthCredentials: OauthCredentials): DbOauthCredentials {
        return DbOauthCredentials(
            accessToken = oauthCredentials.accessToken,
            tokenType = oauthCredentials.tokenType,
            tokenTtl = oauthCredentials.tokenTtl,
            expirationTime = authExpiryTimeCalculator.calculateExpiryTime(oauthCredentials),
        )
    }

    fun mapToDomainOauthCredentials(oauthCredentials: DbOauthCredentials): OauthCredentials {
        return OauthCredentials(
            accessToken = oauthCredentials.accessToken,
            tokenType = oauthCredentials.tokenType,
            tokenTtl = oauthCredentials.tokenTtl,
        )
    }
}
