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

val release by tasks.registering {
    group = "merlin"
    dependsOn("spotlessApply", deployIndexes)
    doLast {
        val version = project.version.toString()
        require(!version.endsWith("-SNAPSHOT")) {
            "Cannot release a snapshot version."
        }
        exec { commandLine = listOf("git", "add", "gradle.properties") }
        exec { commandLine = listOf("git", "commit", "-m", "release: $version") }
    }
}
