import com.android.build.api.dsl.VariantDimension
import com.google.protobuf.gradle.id
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.jetpackCompose)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
}

fun Project.property(key: String, default: String): String {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
    }
    return (localProperties.getProperty(key) ?: providers.gradleProperty(key).orNull ?: default)
}

fun VariantDimension.stringField(name: String, value: String) {
    buildConfigField("String", name, "\"$value\"")
}

fun readProperties(fileName: String): Properties {
    return Properties().apply {
        val file = rootProject.file(fileName)
        if (file.exists()) {
            load(file.inputStream())
        }
    }
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

        stringField("GAMESPOT_API_KEY", property("GAMESPOT_API_KEY", ""))
        stringField("TWITCH_APP_CLIENT_ID", property("TWITCH_APP_CLIENT_ID", ""))
        stringField("TWITCH_APP_CLIENT_SECRET", property("TWITCH_APP_CLIENT_SECRET", ""))
    }

    signingConfigs {
        create("release") {
            val keystoreFile = "keystore.properties"
            if (rootProject.file(keystoreFile).canRead()) {
                val properties = readProperties(keystoreFile)
                storeFile = file(properties.getProperty("storeFile"))
                storePassword = properties.getProperty("storePassword")
                keyAlias = properties.getProperty("keyAlias")
                keyPassword = properties.getProperty("keyPassword")
            } else {
                println("Cannot create a release signing config. The file $keystoreFile does not exist.")
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            manifestPlaceholders["usesCleartextTraffic"] = true
        }
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
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
    
    buildFeatures {
        buildConfig = true
    }
}

kapt {
    correctErrorTypes = true
}

protobuf {
    protoc {
        artifact = libs.protobufCompiler.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.addAll(
            listOf(
                "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
                "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-opt-in=androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi",
                "-opt-in=androidx.constraintlayout.compose.ExperimentalMotionApi",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xsuppress-version-warnings"
            )
        )
    }
}

dependencies {
    implementation(libs.activity)
    implementation(libs.splash)
    implementation(libs.composeNavigation)
    implementation(libs.commonsCore)
    implementation(libs.commonsKtx)
    implementation(libs.kotlinxSerialization)
    implementation(libs.browser)
    implementation(libs.kotlinResult)
    implementation(libs.daggerHiltCore)
    implementation(libs.commonsNetwork)
    implementation(libs.commonsWindowAnims)
    implementation(libs.coil)
    implementation(libs.okHttpLoggingInterceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofitKotlinxSerializationConverter)
    implementation(libs.retrofitScalarsConverter)
    implementation(libs.prefsDataStore)
    implementation(libs.protoDataStore)
    implementation(libs.room)
    implementation(libs.roomKtx)
    implementation(libs.zoomable)
    implementation(libs.materialComponents)
    implementation(libs.composeHilt)

    kapt(libs.daggerHiltCoreCompiler)
    ksp(libs.roomCompiler)

    coreLibraryDesugaring(libs.desugaredJdk)

    implementation(platform(libs.composeBom))
    implementation(libs.composeUi)
    debugImplementation(libs.composeTooling)
    implementation(libs.composeToolingPreview)
    implementation(libs.composeFoundation)
    implementation(libs.composeMaterial)
    implementation(libs.composeRuntime)
    implementation(libs.composeAnimation)
    implementation(libs.composeConstraintLayout)

    implementation(libs.daggerHiltAndroid)
    kapt(libs.daggerHiltAndroidCompiler)

    implementation(libs.coroutines)
    implementation(libs.protobuf)
}

val installGitHook by tasks.registering(Copy::class) {
    from(File(rootProject.rootDir, "hooks/pre-push"))
    into(File(rootProject.rootDir, ".git/hooks/"))

    filePermissions {
        unix("rwxr-xr-x")
    }
}

tasks.getByPath(":app:preBuild").dependsOn(installGitHook)
