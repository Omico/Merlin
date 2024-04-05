import me.omico.gradle.initialization.includeAllSubprojectModules
import me.omico.gradm.addDeclaredRepositories

addDeclaredRepositories()

plugins {
    id("merlin.develocity")
    id("merlin.gradm")
}

includeBuild("build-logic/project")

includeAllSubprojectModules("merlin")
