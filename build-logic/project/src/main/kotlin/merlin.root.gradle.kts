
plugins {
    id("me.omico.consensus.root")
    id("merlin.gradm")
    id("merlin.root.git")
    id("merlin.root.spotless")
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = versions.gradle
    distributionType = Wrapper.DistributionType.BIN
}
