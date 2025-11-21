

plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.gamedgeAndroid.get().pluginId)
    id(libs.plugins.gamedgeDaggerHilt.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.common.api"

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
