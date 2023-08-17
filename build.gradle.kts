plugins {
    id("merlin.root")
}

val deployIndexes by tasks.registering {
    group = "merlin"
    dependsOn(
        ":merlin-google:run",
        ":merlin-omico:run",
    )
}

val version = project.version.toString()

val release by tasks.registering {
    group = "merlin"
    dependsOn("spotlessApply", deployIndexes)
    doLast {
        require(!version.endsWith("-SNAPSHOT")) { "Cannot release a snapshot version." }
        exec { commandLine = listOf("git", "add", "gradle.properties") }
        exec { commandLine = listOf("git", "commit", "-m", "release: $version") }
    }
}

val prepareNextRelease by tasks.registering {
    group = "merlin"
    dependsOn("spotlessApply")
    doLast {
        require(version.endsWith("-SNAPSHOT")) { "Cannot prepare for the next release with a non-snapshot version." }
        exec { commandLine = listOf("git", "add", "gradle.properties") }
        exec { commandLine = listOf("git", "commit", "-m", "Prepare for the next release") }
    }
}
