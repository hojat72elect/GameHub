package com.paulrybitskyi.gamedge.plugins

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.paulrybitskyi.gamedge.extensions.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

class GamedgeAndroidPlugin : Plugin<Project> {

    private companion object {
        const val APPLICATION_ID = "com.paulrybitskyi.gamedge"
        const val BUILD_TYPE_DEBUG = "debug"
        const val BUILD_TYPE_DEBUG_SUFFIX = ".debug"
        const val BUILD_TYPE_RELEASE = "release"
        const val SIGNING_CONFIG_RELEASE = "release"
        const val KEYSTORE_FILE_NAME = "keystore.properties"
    }

    override fun apply(project: Project) = with(project) {
        setupPlugins()
        configurePlugins()
        addDependencies()
    }

    private fun Project.setupPlugins(): Unit = with(plugins) {
        apply(libs.plugins.kotlinAndroid.get().pluginId)
        apply(libs.plugins.gamedgeKotlinCoroutines.get().pluginId)
    }

    private fun Project.configurePlugins() {
        configureAndroidCommonInfo()
        configureAndroidApplication()
    }

    private fun Project.configureAndroidCommonInfo() = configure<BaseExtension> {
        compileSdkVersion(libs.versions.compileSdk.get().toInt())

        defaultConfig {
            minSdk = libs.versions.minSdk.get().toInt()
            targetSdk = libs.versions.targetSdk.get().toInt()
            versionCode = libs.versions.appVersionCode.get().toInt()
            versionName = libs.versions.appVersionName.get()
        }

        buildTypes {
            getByName(BUILD_TYPE_DEBUG) {
                // Enabling accessing sites with http schemas for testing (especially
                // instrumented tests using MockWebServer) and disabling it in the
                // production to avoid security issues
                manifestPlaceholders["usesCleartextTraffic"] = true
            }

            getByName(BUILD_TYPE_RELEASE) {
                manifestPlaceholders["usesCleartextTraffic"] = false

                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }

        compileOptions {
            val javaVersion = JavaVersion.toVersion(libs.versions.jvmToolchain.get().toInt())

            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
            isCoreLibraryDesugaringEnabled = true
        }

    }

    private fun Project.configureAndroidApplication() {
        plugins.withId(libs.plugins.androidApplication.get().pluginId) {
            configure<BaseAppModuleExtension> {
                namespace = APPLICATION_ID

                defaultConfig {
                    applicationId = APPLICATION_ID
                }

                signingConfigs {
                    create(SIGNING_CONFIG_RELEASE) {
                        if (rootProject.file(KEYSTORE_FILE_NAME).canRead()) {
                            val properties = readProperties(KEYSTORE_FILE_NAME)

                            storeFile = file(properties.getValue("storeFile"))
                            storePassword = properties.getValue("storePassword")
                            keyAlias = properties.getValue("keyAlias")
                            keyPassword = properties.getValue("keyPassword")
                        } else {
                            println(
                                """
                                Cannot create a release signing config. The file,
                                $KEYSTORE_FILE_NAME, either does not exist or
                                cannot be read from.
                            """.trimIndent()
                            )
                        }
                    }
                }

                buildTypes {
                    getByName(BUILD_TYPE_DEBUG) {
                        applicationIdSuffix = BUILD_TYPE_DEBUG_SUFFIX
                    }
                    getByName(BUILD_TYPE_RELEASE) {
                        isMinifyEnabled = true
                        signingConfig = signingConfigs.getByName(SIGNING_CONFIG_RELEASE)
                    }
                }
            }
        }
    }

    private fun Project.readProperties(fileName: String): Properties {
        return Properties().apply {
            load(rootProject.file(fileName).inputStream())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> Properties.getValue(key: String): T {
        return (get(key) as T)
    }

    private fun Project.addDependencies(): Unit = with(dependencies) {
        add("implementation", libs.kotlinResult.get())
        add("coreLibraryDesugaring", libs.desugaredJdk.get())
    }
}
