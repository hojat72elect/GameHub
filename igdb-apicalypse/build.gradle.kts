
plugins {
    id(libs.plugins.kotlinJvm.get().pluginId)
}

dependencies {
    testImplementation(libs.jUnit)
    testImplementation(libs.truth)
}
