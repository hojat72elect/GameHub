/*
 * Copyright 2020 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.jetpackCompose)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "ca.six.hojat.gamehub"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ca.six.hojat.gamehub"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.appVersionCode.get().toInt()
        versionName = libs.versions.appVersionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "GAMESPOT_API_KEY", "\"${localProperties.getProperty("GAMESPOT_API_KEY")}\"")
        buildConfigField("String", "TWITCH_APP_CLIENT_ID", "\"${localProperties.getProperty("TWITCH_APP_CLIENT_ID")}\"")
        buildConfigField("String", "TWITCH_APP_CLIENT_SECRET", "\"${localProperties.getProperty("TWITCH_APP_CLIENT_SECRET")}\"")
    }

    signingConfigs {
        create("release") {
            // These environment variables are set on the CI/CD
            storeFile = file("prod.keystore")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isMinifyEnabled = false
            isDebuggable = true
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        freeCompilerArgs += "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        freeCompilerArgs += "-opt-in=kotlinx.coroutines.FlowPreview"
        freeCompilerArgs += "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
        freeCompilerArgs += "-opt-in=androidx.compose.animation.ExperimentalAnimationApi"
        freeCompilerArgs += "-opt-in=androidx.compose.material.ExperimentalMaterialApi"
        freeCompilerArgs += "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
        freeCompilerArgs += "-opt-in=com.google.accompanist.pager.ExperimentalPagerApi"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.coroutines)
    implementation(libs.kotlinxSerialization)

    implementation(libs.activity)
    implementation(libs.materialComponents)
    implementation(libs.splash)
    implementation(libs.browser)

    implementation(libs.room)
    implementation(libs.roomKtx)
    ksp(libs.roomCompiler)

    implementation(libs.daggerHiltAndroid)
    kapt(libs.daggerHiltAndroidCompiler)
    implementation(libs.composeHilt)

    implementation(libs.okHttpLoggingInterceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofitKotlinxSerializationConverter)
    implementation(libs.retrofitScalarsConverter)

    implementation(platform(libs.composeBom))
    implementation(libs.composeUi)
    implementation(libs.composeToolingPreview)
    implementation(libs.composeFoundation)
    implementation(libs.composeMaterial)
    implementation(libs.composeRuntime)
    implementation(libs.composeConstraintLayout)
    implementation(libs.composeNavigation)

    debugImplementation(libs.composeTooling)

    implementation(libs.coil)
    implementation(libs.zoomable)

    implementation(libs.kotlinResult)
}
