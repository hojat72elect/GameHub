import com.paulrybitskyi.gamedge.extensions.property
import com.paulrybitskyi.gamedge.extensions.stringField

plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.gamedgeAndroid.get().pluginId)
    id(libs.plugins.gamedgeKotlinCoroutines.get().pluginId)
    id(libs.plugins.gamedgeKotlinKapt.get().pluginId)
    id(libs.plugins.gamedgeJetpackCompose.get().pluginId)
    id(libs.plugins.gamedgeDaggerHilt.get().pluginId)
    id(libs.plugins.gamedgeProtobuf.get().pluginId)
    id(libs.plugins.gamedgeRemoteApi.get().pluginId)
    id(libs.plugins.gamedgeKotlinxSerialization.get().pluginId)

    alias(libs.plugins.ksp)
}

android {
    namespace = "com.paulrybitskyi.gamedge.core"

    defaultConfig {
        stringField("GAMESPOT_API_KEY", property("GAMESPOT_API_KEY", ""))
        stringField("TWITCH_APP_CLIENT_ID", property("TWITCH_APP_CLIENT_ID", ""))
        stringField("TWITCH_APP_CLIENT_SECRET", property("TWITCH_APP_CLIENT_SECRET", ""))
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.kotlinxSerialization)
    implementation(libs.browser)
    implementation(libs.kotlinResult)
    implementation(libs.commonsCore)
    implementation(libs.daggerHiltCore)
    implementation(libs.commonsKtx)
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
}
