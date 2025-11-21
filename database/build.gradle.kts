
plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.gamedgeAndroid.get().pluginId)
    id(libs.plugins.gamedgeDaggerHilt.get().pluginId)
    id(libs.plugins.gamedgeKotlinxSerialization.get().pluginId)

    alias(libs.plugins.ksp)
}

android {
    namespace = "com.paulrybitskyi.gamedge.database"

    sourceSets {
        getByName("androidTest").assets.srcDirs("$projectDir/schemas")
    }

    lint {
        // Fix an error "Error: EntityInsertionAdapter can only be accessed from within
        // the same library group prefix (referenced groupId=androidx.room with prefix
        // androidx from groupId=Gamedge) [RestrictedApi]
        disable.add("RestrictedApi")
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(project(localModules.core))

    implementation(libs.room)
    implementation(libs.roomKtx)
    ksp(libs.roomCompiler)

    testImplementation(project(localModules.commonTesting))
    testImplementation(libs.bundles.testing)

    androidTestImplementation(libs.bundles.testingAndroid)
    androidTestImplementation(libs.archCore)
    androidTestImplementation(libs.roomTesting)
}
