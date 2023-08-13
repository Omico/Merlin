import me.omico.gradm.addDeclaredRepositories
import me.omico.merlin.initialization.internal.includeMerlinModules

addDeclaredRepositories()

plugins {
    id("merlin.gradm")
    id("merlin.gradle-enterprise")
}

includeBuild("build-logic/project")

includeMerlinModules()
