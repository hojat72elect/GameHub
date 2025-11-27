package ca.six.hojat.gamehub.feature.settings.data.datastores

import androidx.datastore.core.Serializer
import ca.six.hojat.gamehub.feature.settings.domain.DomainSettings
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
internal object ProtoSettingsSerializer : Serializer<ProtoSettings> {

    override val defaultValue: ProtoSettings
        get() = ProtoSettings.newBuilder()
            .setThemeName(DomainSettings.DEFAULT.theme.name)
            .build()

    override suspend fun writeTo(t: ProtoSettings, output: OutputStream) = t.writeTo(output)
    override suspend fun readFrom(input: InputStream): ProtoSettings = ProtoSettings.parseFrom(input)
}
