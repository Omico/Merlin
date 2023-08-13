// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
@file:JvmName("MerlinGoogleCli")
@file:OptIn(ExperimentalSerializationApi::class)

package me.omico.merlin.google

import kotlinx.cli.ArgParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.encodeToString
import kotlinx.serialization.protobuf.ProtoBuf
import me.omico.merlin.Dependencies
import me.omico.merlin.Dependency
import me.omico.merlin.google.internal.utility.PathType
import me.omico.merlin.google.internal.utility.clearDirectory
import me.omico.merlin.google.internal.utility.json
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import kotlin.io.path.writeBytes
import kotlin.io.path.writeText

fun main(arguments: Array<String>) {
    val parser = ArgParser(programName = "merlin-google-cli")
    val outDirectory by parser.argument(type = PathType)
    parser.parse(arguments)
    outDirectory.clearDirectory()
    val dependencies = fetchGoogleDependencies()
    outDirectory.resolve("google-index.json").writeText(json.encodeToString(dependencies))
    outDirectory.resolve("google-index").writeBytes(ProtoBuf.encodeToByteArray(dependencies))
    outDirectory.resolve("google-index.hash").writeText(dependencies.hashCode().toString())
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
