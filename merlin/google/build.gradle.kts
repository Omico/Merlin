plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
}

application {
    mainClass.set("me.omico.merlin.google.MerlinGoogleCli")
}

dependencies {
    implementation(project(":merlin-runtime"))
}

dependencies {
    implementation(kotlinx.cli)
}

tasks.run<JavaExec> {
    args(rootProject.buildDir.resolve("merlin").absolutePath)
}