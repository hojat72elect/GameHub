
plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.gamedgeAndroid.get().pluginId)
    id(libs.plugins.gamedgeDaggerHilt.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.core"
}

dependencies {
    implementation(project(localModules.commonDomain))

    implementation(libs.kotlinxSerialization)

    implementation(libs.browser)

    implementation(libs.commonsCore)
    implementation(libs.commonsKtx)
    implementation(libs.commonsNetwork)
    implementation(libs.commonsWindowAnims)

    testImplementation(project(localModules.commonTesting))
    testImplementation(libs.bundles.testing)
}
