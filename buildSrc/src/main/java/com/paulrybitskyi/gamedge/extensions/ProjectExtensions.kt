
package com.paulrybitskyi.gamedge.extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import java.util.Properties

val Project.libs
    get() = the<org.gradle.accessors.dm.LibrariesForLibs>()

@Suppress("UNCHECKED_CAST")
fun <T> Project.property(key: String, default: T): T {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")

    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
    }

    return ((localProperties[key] as? T) ?: (properties[key] as? T) ?: default)
}
