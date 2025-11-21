

plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.gamedgeAndroid.get().pluginId)
    id(libs.plugins.gamedgeKotlinCoroutines.get().pluginId)
    id(libs.plugins.gamedgeKotlinKapt.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.common.testing"
}

dependencies {
    api(project(localModules.commonTestingDomain))
    implementation(project(localModules.core))
    implementation(project(localModules.commonApi))

    // Unit tests
    implementation(libs.jUnit)
    implementation(libs.mockk)
    implementation(libs.coroutinesTesting)

    // Instrumentation tests
    implementation(libs.testRunner)
    implementation(libs.mockWebServer)

    implementation(libs.daggerHiltTesting)
    kapt(libs.daggerHiltAndroidCompiler)
}
