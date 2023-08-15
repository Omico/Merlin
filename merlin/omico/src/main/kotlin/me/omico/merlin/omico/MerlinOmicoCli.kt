// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
@file:JvmName("MerlinOmicoCli")

package me.omico.merlin.omico

import kotlinx.cli.ArgParser
import me.omico.merlin.Dependency
import me.omico.merlin.IndexFiles
import me.omico.merlin.cli.PathType
import me.omico.merlin.cli.save
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.File
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.name
import kotlin.io.path.pathString
import kotlin.io.path.readText
import kotlin.io.path.relativeTo
import kotlin.io.path.walk

fun main(arguments: Array<String>) {
    val parser = ArgParser(programName = "merlin-omico-cli")
    val repositoryDirectory by parser.argument(type = PathType)
    val outDirectory by parser.argument(type = PathType)
    parser.parse(arguments)
    IndexFiles.Omico.save(outDirectory, collectDependencies(repositoryDirectory))
}

@OptIn(ExperimentalPathApi::class)
private fun collectDependencies(repositoryDirectory: Path): List<Dependency> =
    repositoryDirectory.walk()
        .filter { it.name == "maven-metadata.xml" }
        .map { metadataFile -> Dependency(repositoryDirectory, metadataFile) }
        .toList()

private fun Dependency(repositoryDirectory: Path, metadataFile: Path): Dependency = run {
    val metadataFilePath = metadataFile.relativeTo(repositoryDirectory).pathString
    val paths = metadataFilePath.split(File.separator).dropLast(1)
    val group = paths.dropLast(1).joinToString(".")
    val artifact = paths.last()
    val versions = Jsoup.parse(metadataFile.readText())
        .getElementsByTag("versions").first()
        ?.getElementsByTag("version")
        ?.map(Element::text)
        ?: emptyList()
    Dependency(
        group = group,
        artifact = artifact,
        versions = versions,
    )
}
