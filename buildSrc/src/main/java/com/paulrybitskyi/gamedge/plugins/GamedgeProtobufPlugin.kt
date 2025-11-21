package com.paulrybitskyi.gamedge.plugins

import com.google.protobuf.gradle.ProtobufExtension
import com.google.protobuf.gradle.id
import com.paulrybitskyi.gamedge.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class GamedgeProtobufPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        setupPlugin()
        configurePlugin()
        addProtobufDependency()
    }

    private fun Project.setupPlugin() {
        plugins.apply(libs.plugins.protobuf.get().pluginId)
    }

    private fun Project.configurePlugin() {
        configure<ProtobufExtension> {
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
    }

    private fun Project.addProtobufDependency() {
        dependencies.add("implementation", libs.protobuf.get())
    }
}
