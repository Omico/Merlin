// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
package me.omico.merlin.cli

import me.omico.merlin.Dependencies
import me.omico.merlin.IndexFile
import me.omico.merlin.cli.internal.saveAsJson
import me.omico.merlin.cli.internal.saveAsProtoBuf
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.writeText

fun IndexFile.save(outDirectory: Path, dependencies: Dependencies) {
    outDirectory.createDirectories()
    outDirectory.resolve(name).saveAsProtoBuf(dependencies)
    outDirectory.resolve(json).saveAsJson(dependencies)
    outDirectory.resolve(hash).writeText(dependencies.hashCode().toString())
}
