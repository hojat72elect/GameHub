
plugins {
    id(libs.plugins.gamedgeFeature.get().pluginId)
    id(libs.plugins.gamedgeProtobuf.get().pluginId)
}

android {
    namespace = "com.paulrybitskyi.gamedge.feature.settings"
}

dependencies {
    implementation(libs.protoDataStore)
}
