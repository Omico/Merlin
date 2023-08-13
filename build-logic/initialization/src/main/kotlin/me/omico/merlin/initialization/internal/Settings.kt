package me.omico.merlin.initialization.internal

import org.gradle.api.initialization.Settings
import java.io.File

internal fun Settings.includeMerlinModules() {
    rootDir.resolve("merlin").walk().maxDepth(1)
        .drop(1)
        .filter { it.isDirectory && it.resolve("build.gradle.kts").exists() }
        .map(File::getName)
        .forEach(::includeMerlin)
}

private fun Settings.includeMerlin(name: String) {
    include(":$name")
    project(":$name").run {
        this.name = "merlin-$name"
        this.projectDir = rootDir.resolve("merlin").resolve(name)
    }
}
