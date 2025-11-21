
import com.paulrybitskyi.gamedge.extensions.property
import com.paulrybitskyi.gamedge.extensions.stringField

plugins {
    id(libs.plugins.gamedgeRemoteApi.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.igdb.api"

    defaultConfig {
        stringField("TWITCH_APP_CLIENT_ID", property("TWITCH_APP_CLIENT_ID", ""))
        stringField("TWITCH_APP_CLIENT_SECRET", property("TWITCH_APP_CLIENT_SECRET", ""))
    }
}

dependencies {
    implementation(project(localModules.igdbApicalypse))

    implementation(libs.retrofitScalarsConverter)
}
