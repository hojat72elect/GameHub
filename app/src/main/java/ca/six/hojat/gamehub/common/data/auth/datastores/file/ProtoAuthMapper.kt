package ca.six.hojat.gamehub.common.data.auth.datastores.file

import ca.six.hojat.gamehub.common.domain.auth.entities.OauthCredentials
import javax.inject.Inject

internal class ProtoAuthMapper @Inject constructor(
    private val authExpiryTimeCalculator: AuthExpiryTimeCalculator,
) {

    fun mapToProtoOauthCredentials(oauthCredentials: OauthCredentials): ProtoOauthCredentials {
        return ProtoOauthCredentials.newBuilder()
            .setAccessToken(oauthCredentials.accessToken)
            .setTokenType(oauthCredentials.tokenType)
            .setTokenTtl(oauthCredentials.tokenTtl)
            .setExpirationTime(authExpiryTimeCalculator.calculateExpiryTime(oauthCredentials))
            .build()
    }

    fun mapToDomainOauthCredentials(oauthCredentials: ProtoOauthCredentials): OauthCredentials {
        return OauthCredentials(
            accessToken = oauthCredentials.accessToken,
            tokenType = oauthCredentials.tokenType,
            tokenTtl = oauthCredentials.tokenTtl,
        )
    }
}
