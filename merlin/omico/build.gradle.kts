plugins {
    id("merlin.cli")
}

application {
    mainClass.set("me.omico.merlin.omico.MerlinOmicoCli")
}

tasks.run<JavaExec> {
    args(
        file(consensus.sensitiveProperty<String>("MAVEN_OMICO_LOCAL_URI")).absolutePath,
        rootProject.buildDir.resolve("merlin").absolutePath,
    )
}
