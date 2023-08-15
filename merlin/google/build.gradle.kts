plugins {
    id("merlin.cli")
}

application {
    mainClass.set("me.omico.merlin.google.MerlinGoogleCli")
}

tasks.run<JavaExec> {
    args(rootProject.buildDir.resolve("merlin").absolutePath)
}
