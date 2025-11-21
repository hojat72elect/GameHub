

import com.paulrybitskyi.gamedge.extensions.property
import com.paulrybitskyi.gamedge.extensions.stringField

plugins {
    id(libs.plugins.gamedgeRemoteApi.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.gamespot.api"

    defaultConfig {
        stringField("GAMESPOT_API_KEY", property("GAMESPOT_API_KEY", ""))
    }
}

dependencies {
    implementation(libs.retrofitKotlinxSerializationConverter)
}
