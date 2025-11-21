
plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.gamedgeAndroid.get().pluginId)
    id(libs.plugins.gamedgeJetpackCompose.get().pluginId)
    id(libs.plugins.gamedgeDaggerHilt.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.common.ui"
}

dependencies {
    implementation(project(localModules.core))

    implementation(libs.commonsCore)
    implementation(libs.commonsKtx)

    implementation(libs.coil)
}
