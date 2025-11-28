
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.protobuf) apply false

    alias(libs.plugins.jetpackCompose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.kotlinxSerialization) apply false

    alias(libs.plugins.gradleVersions) apply true
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        listOf("alpha", "beta", "rc").any { keyword ->
            candidate.version.lowercase().contains(keyword)
        }
    }
}

allprojects {

    repositories {
        mavenCentral()
        google()
        maven { setUrl("https://jitpack.io") }
    }
}

subprojects {
    plugins.withId(rootProject.libs.plugins.kotlinJvm.get().pluginId) {
        configure<KotlinProjectExtension> {
            jvmToolchain(rootProject.libs.versions.jvmToolchain.get().toInt())
        }
    }

    plugins.withId(rootProject.libs.plugins.kotlinAndroid.get().pluginId) {
        configure<KotlinProjectExtension> {
            jvmToolchain(rootProject.libs.versions.jvmToolchain.get().toInt())
        }
    }

    // https://stackoverflow.com/a/70348822/7015881
    // https://issuetracker.google.com/issues/238425626
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "androidx.lifecycle" && requested.name == "lifecycle-viewmodel-ktx") {
                useVersion(rootProject.libs.versions.viewModel.get())
            }
        }
    }
}

val clean by tasks.registering(Delete::class) {
    delete(layout.buildDirectory)
}
