plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("merlin.publishing")
}

dependencies {
    compileOnly(kotlinx.serialization.core)
}
