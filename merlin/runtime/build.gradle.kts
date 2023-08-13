plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    api(jsoup)
    api(kotlinx.coroutines.core)
    api(kotlinx.serialization.json)
    api(kotlinx.serialization.protobuf)
}
