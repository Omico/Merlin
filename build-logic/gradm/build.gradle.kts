plugins {
    `kotlin-dsl`
    id("me.omico.gradm") version "4.0.0-beta02"
}

repositories {
    mavenCentral()
}

gradm {
    pluginId = "merlin.gradm"
    debug = true
}