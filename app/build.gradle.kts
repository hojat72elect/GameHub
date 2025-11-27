
plugins {
    id(libs.plugins.androidApplication.get().pluginId)
    id(libs.plugins.gamedgeAndroid.get().pluginId)
    id(libs.plugins.gamedgeJetpackCompose.get().pluginId)
    id(libs.plugins.gamedgeDaggerHilt.get().pluginId)
}

dependencies {

    implementation(project(localModules.core))
    implementation(project(localModules.featureSettings))

    implementation(libs.activity)
    implementation(libs.splash)
    implementation(libs.composeNavigation)
    implementation(libs.commonsCore)
    implementation(libs.commonsKtx)
    implementation(libs.kotlinxSerialization)
}

val installGitHook by tasks.registering(Copy::class) {
    from(File(rootProject.rootDir, "hooks/pre-push"))
    into(File(rootProject.rootDir, ".git/hooks/"))

    // https://github.com/gradle/kotlin-dsl-samples/issues/1412
    filePermissions {
        unix("rwxr-xr-x")
    }
}

tasks.getByPath(":app:preBuild").dependsOn(installGitHook)
