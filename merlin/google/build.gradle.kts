plugins {
    id("merlin.cli")
}

application {
    mainClass.set("me.omico.merlin.google.MerlinGoogleCli")
}

tasks.run<JavaExec> {
    args(rootProject.layout.buildDirectory.dir("merlin").get().asFile.absolutePath)
}
