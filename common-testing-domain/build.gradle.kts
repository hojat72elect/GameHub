

plugins {
    id(libs.plugins.kotlinJvm.get().pluginId)
    id(libs.plugins.gamedgeKotlinCoroutines.get().pluginId)
}

dependencies {
    implementation(project(localModules.commonDomain))

    implementation(libs.jUnit)
    implementation(libs.mockk)
    implementation(libs.coroutinesTesting)
}
