package ca.six.hojat.gamehub.feature.settings.data.datastores

import ca.six.hojat.gamehub.feature.settings.domain.entities.Settings
import ca.six.hojat.gamehub.feature.settings.domain.entities.Theme
import javax.inject.Inject

internal class ProtoSettingsMapper @Inject constructor() {

    fun mapToProtoSettings(settings: Settings): ProtoSettings {
        return ProtoSettings.newBuilder()
            .setThemeName(settings.theme.name)
            .build()
    }

    fun mapToDomainSettings(protoSettings: ProtoSettings): Settings {
        return Settings(
            theme = Theme.valueOf(protoSettings.themeName),
        )
    }
}
