
plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.gamedgeAndroid.get().pluginId)
    id(libs.plugins.gamedgeJetpackCompose.get().pluginId)
    id(libs.plugins.gamedgeDaggerHilt.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.common.ui.widgets"
}

dependencies {
    implementation(project(localModules.commonDomain))
    implementation(project(localModules.core))
    implementation(project(localModules.commonUi))

    implementation(libs.coil)
}
