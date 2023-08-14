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
    implementation(jsoup)
    implementation(kotlinx.cli)
    implementation(kotlinx.coroutines.core)
    implementation(kotlinx.serialization.json)
    implementation(kotlinx.serialization.protobuf)
}

tasks.run<JavaExec> {
    args(rootProject.buildDir.resolve("merlin").absolutePath)
}
