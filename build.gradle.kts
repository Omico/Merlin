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
