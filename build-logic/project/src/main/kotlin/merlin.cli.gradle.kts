plugins {
    id("merlin.gradm")
    application
    kotlin("jvm")
}

dependencies {
    implementation(project(":merlin-runtime"))
}

// For Merlin CLI implementation
dependencies {
    if (project.name == "merlin-cli") return@dependencies
    implementation(project(":merlin-cli"))
}

dependencies {
    implementation(jsoup)
    implementation(kotlinx.cli)
    implementation(kotlinx.coroutines.core)
}
