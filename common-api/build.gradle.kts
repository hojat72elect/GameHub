import com.paulrybitskyi.gamedge.extensions.property
import com.paulrybitskyi.gamedge.extensions.stringField


plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.gamedgeAndroid.get().pluginId)
    id(libs.plugins.gamedgeDaggerHilt.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.common.api"

    defaultConfig {
        stringField("GAMESPOT_API_KEY", property("GAMESPOT_API_KEY", ""))
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(localModules.core))

    implementation(libs.kotlinxSerialization)

    implementation(libs.okHttpLoggingInterceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofitKotlinxSerializationConverter)

    implementation(libs.kotlinResult)
}
