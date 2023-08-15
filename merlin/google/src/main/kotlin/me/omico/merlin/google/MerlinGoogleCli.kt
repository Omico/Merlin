// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
@file:JvmName("MerlinGoogleCli")

package me.omico.merlin.google

import kotlinx.cli.ArgParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import me.omico.merlin.Dependencies
import me.omico.merlin.Dependency
import me.omico.merlin.IndexFiles
import me.omico.merlin.cli.PathType
import me.omico.merlin.cli.save
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

fun main(arguments: Array<String>) {
    val parser = ArgParser(programName = "merlin-google-cli")
    val outDirectory by parser.argument(type = PathType)
    parser.parse(arguments)
    IndexFiles.Google.save(outDirectory, fetchGoogleDependencies())
}

private fun fetchGoogleDependencies(): Dependencies = runBlocking {
    coroutineScope {
        fetchMasterIndex()
            .map { group ->
                async(Dispatchers.IO) {
                    fetchGroupIndex(group).map { (artifact, versions) -> Dependency(group, artifact, versions) }
                }
            }
            .awaitAll()
            .flatten()
    }
}

private const val BASE_URL = "https://dl.google.com/android/maven2"
private const val MASTER_INDEX_URL = "$BASE_URL/master-index.xml"
private const val GROUP_INDEX_URL = "$BASE_URL/{GROUP_PATH}/group-index.xml"

private fun fetchMasterIndex(): Set<String> =
    Jsoup.connect(MASTER_INDEX_URL).get().getElementsByTag("metadata").first()
        ?.children()?.map(Element::tagName)
        ?.toSet() ?: emptySet()

private fun fetchGroupIndex(group: String): Map<String, List<String>> {
    val groupPath = group.replace(".", "/")
    val element = Jsoup.connect(GROUP_INDEX_URL.replace("{GROUP_PATH}", groupPath)).get()
        .getElementsByTag(group).first() ?: return emptyMap()
    return element.children().associate { it.tagName() to it.attr("versions").split(",") }
}
