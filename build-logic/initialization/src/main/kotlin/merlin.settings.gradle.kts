import me.omico.gradle.initialization.includeAllSubprojectModules
import me.omico.gradm.addDeclaredRepositories

addDeclaredRepositories()

plugins {
    id("merlin.gradm")
    id("merlin.gradle-enterprise")
}

includeBuild("build-logic/project")

includeAllSubprojectModules("merlin")
