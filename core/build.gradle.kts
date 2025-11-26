
plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.gamedgeAndroid.get().pluginId)
    id(libs.plugins.gamedgeKotlinCoroutines.get().pluginId)
    id(libs.plugins.gamedgeKotlinKapt.get().pluginId)
    id(libs.plugins.gamedgeJetpackCompose.get().pluginId)
    id(libs.plugins.gamedgeDaggerHilt.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.core"
}

dependencies {
    implementation(libs.kotlinxSerialization)
    implementation(libs.browser)
    implementation(libs.kotlinResult)
    implementation(libs.commonsCore)
    implementation(libs.daggerHiltCore)
    implementation(libs.commonsKtx)
    implementation(libs.commonsNetwork)
    implementation(libs.commonsWindowAnims)
    implementation(libs.coil)

    kapt(libs.daggerHiltCoreCompiler)
}
