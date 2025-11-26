
plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.gamedgeAndroid.get().pluginId)
    id(libs.plugins.gamedgeProtobuf.get().pluginId)
    id(libs.plugins.gamedgeDaggerHilt.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.common.data"
}

dependencies {
    implementation(project(localModules.core))
    implementation(project(localModules.database))

    implementation(libs.prefsDataStore)
    implementation(libs.protoDataStore)
}
