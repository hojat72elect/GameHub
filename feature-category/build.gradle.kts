plugins {
    id(libs.plugins.gamedgeFeature.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.feature.category"
}

dependencies {
    implementation(project(localModules.gamespotApi))
    implementation(project(localModules.database))

    implementation(libs.prefsDataStore)
    implementation(libs.zoomable)
    implementation(libs.materialComponents)
}