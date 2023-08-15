// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
package me.omico.merlin.cli.internal

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.writeText

internal val prettyJson: Json = Json {
    prettyPrint = true
}

internal inline fun <reified T : Any> Path.saveAsJson(value: T): Unit = writeText(prettyJson.encodeToString(value))
