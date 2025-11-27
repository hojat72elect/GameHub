package ca.six.hojat.gamehub.common.data.auth.datastores.file

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream

internal fun ProtoOauthCredentials.isNotEmpty(): Boolean {
    return (
            accessToken.isNotEmpty() &&
                    tokenType.isNotEmpty() &&
                    (tokenTtl != 0L) &&
                    (expirationTime != 0L)
            )
}

internal object ProtoOauthCredentialsSerializer : Serializer<ProtoOauthCredentials> {

    override val defaultValue: ProtoOauthCredentials
        get() = ProtoOauthCredentials.newBuilder()
            .setAccessToken("")
            .setTokenType("")
            .setTokenTtl(0L)
            .setExpirationTime(0L)
            .build()

    override suspend fun writeTo(t: ProtoOauthCredentials, output: OutputStream) = t.writeTo(output)
    override suspend fun readFrom(input: InputStream): ProtoOauthCredentials = ProtoOauthCredentials.parseFrom(input)
}
