// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
package me.omico.merlin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Dependency {
    val group: String
    val artifact: String
    val versions: List<String>
}

@Serializable
@SerialName("library")
data class Library(
    override val group: String,
    override val artifact: String,
    override val versions: List<String>,
) : Dependency {
    val module: String = "$group:$artifact"
}

@Serializable
@SerialName("plugin")
data class Plugin(
    override val group: String,
    override val artifact: String,
    override val versions: List<String>,
) : Dependency {
    val id: String = group
}

fun Dependency(group: String, artifact: String, versions: List<String>): Dependency =
    when {
        isPlugin(group, artifact) -> Plugin(group, artifact, versions)
        else -> Library(group, artifact, versions)
    }

val Dependency.isPlugin: Boolean
    get() = this is Plugin || isPlugin(group, artifact)

private fun isPlugin(group: String, artifact: String): Boolean = artifact == group + GRADLE_PLUGIN_SUFFIX

private const val GRADLE_PLUGIN_SUFFIX = ".gradle.plugin"
