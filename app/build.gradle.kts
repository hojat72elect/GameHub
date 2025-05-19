import ca.hojat.gamehub.extensions.property
import ca.hojat.gamehub.extensions.stringField

plugins {
    id(PLUGIN_ANDROID_APPLICATION)
    id(PLUGIN_GAMENEWS_ANDROID)
    id(PLUGIN_GAMENEWS_PROTOBUF)
    id(PLUGIN_KOTLIN_KAPT)
    id(PLUGIN_KSP) version Tooling.kspPlugin
    id(PLUGIN_KOTLINX_SERIALIZATION) version Tooling.kotlin
    id(PLUGIN_DAGGER_HILT_ANDROID)
}

android {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    defaultConfig {
        stringField("TWITCH_APP_CLIENT_ID", property("TWITCH_APP_CLIENT_ID", ""))
        stringField("TWITCH_APP_CLIENT_SECRET", property("TWITCH_APP_CLIENT_SECRET", ""))
        stringField("GAMESPOT_API_KEY", property("GAMESPOT_API_KEY", ""))

        resConfigs("en", "fa", "ru")
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }

    // Without the below block, a build failure was happening when running ./gradlew connectedAndroidTest
    // See: https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-debug#debug-agent-and-android
    packagingOptions {
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/LICENSE-notice.md")
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/LICENSE")
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/license.txt")
        resources.excludes.add("META-INF/NOTICE")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("META-INF/notice.txt")
        resources.excludes.add("META-INF/ASL2.0")
        resources.excludes.add("META-INF/*.kotlin_module")
        // for JNA and JNA-platform
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
        // for byte-buddy
        resources.excludes.add("META-INF/licenses/ASM")
        resources.pickFirsts.add("win32-x86-64/attach_hotspot_windows.dll")
        resources.pickFirsts.add("win32-x86/attach_hotspot_windows.dll")
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
        }
    }

    signingConfigs {
        getByName("release") {
            storeFile = file("/Users/hojat.ghasemi/Documents/Android/key/upload-keystore.jks")
            keyPassword = "j@va1android"
            storePassword = "j@va1android"
            keyAlias = "uploadkey"
        }
    }
}

dependencies {

    // appcompat
    implementation(AndroidX.appcompat)
    implementation(AndroidX.appcompatResources)

    // general jetpack libs
    implementation(AndroidX.protoDataStore)
    implementation(AndroidX.splash)
    implementation(AndroidX.prefsDataStore)
    implementation(AndroidX.browser)

    // Logging
    implementation(Tooling.napier)

    // Jetpack compose + Accompanist
    implementation(Compose.ui)
    implementation(Compose.tooling)
    implementation(Compose.foundation)
    implementation(Compose.material)
    implementation(Compose.runtime)
    implementation(Compose.navigation)
    implementation(Compose.animation)
    implementation(Compose.constraintLayout)
    implementation(Compose.hilt)
    implementation(Compose.flowLayout)
    implementation(Compose.pager)
    implementation(Compose.navigationAnimations)
    implementation(Compose.systemUi)
    implementation(Compose.swipeRefresh)
    implementation(Compose.webView)
    // lottie (compose version)
    implementation(Compose.lottie)

    // DI
    implementation(Hilt.daggerHiltAndroid)
    androidTestImplementation(Hilt.daggerHiltTest)
    kapt(Hilt.daggerHiltAndroidCompiler)

    // Needed tools
    implementation(Tooling.coroutines)
    implementation(Tooling.serialization)
    coreLibraryDesugaring(Tooling.desugaredLibs)
    implementation(Tooling.coroutinesTest)

    // http client
    implementation(Network.okHttpLoggingInterceptor)
    implementation(Network.retrofit)
    implementation(Network.retrofitKotlinxSerializationConverter)
    implementation(Network.retrofitScalarsConverter)

    // Room database
    implementation(AndroidX.room)
    implementation(AndroidX.roomKtx)
    ksp(AndroidX.roomCompiler)
    androidTestImplementation(AndroidX.roomTest)

    implementation(ThirdParties.hiltBinder)
    ksp(ThirdParties.hiltBinderCompiler)
    implementation(ThirdParties.kotlinResult)
    implementation(ThirdParties.coil)
    implementation(ThirdParties.zoomable)
}

val installGitHook by tasks.registering(Copy::class) {
    from(File(rootProject.rootDir, "hooks/pre-push"))
    into(File(rootProject.rootDir, ".git/hooks/"))
    // https://github.com/gradle/kotlin-dsl-samples/issues/1412
    fileMode = 0b111101101 // -rwxr-xr-x
}

tasks.getByPath(":app:preBuild").dependsOn(installGitHook)
