
plugins {
    id(libs.plugins.kotlinJvm.get().pluginId)
    id(libs.plugins.gamedgeKotlinCoroutines.get().pluginId)
    id(libs.plugins.gamedgeKotlinKapt.get().pluginId)
}

dependencies {
    implementation(libs.kotlinResult)

    implementation(libs.daggerHiltCore)
    kapt(libs.daggerHiltCoreCompiler)
}
