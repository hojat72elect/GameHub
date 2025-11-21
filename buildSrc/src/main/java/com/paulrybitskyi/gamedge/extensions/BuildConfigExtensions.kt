package com.paulrybitskyi.gamedge.extensions

import com.android.build.api.dsl.VariantDimension

fun VariantDimension.booleanField(name: String, value: Boolean) {
    buildConfigField("Boolean", name, value.toString())
}

fun VariantDimension.integerField(name: String, value: Int) {
    buildConfigField("Integer", name, value.toString())
}

fun VariantDimension.stringField(name: String, value: String) {
    buildConfigField("String", name, "\"$value\"")
}
