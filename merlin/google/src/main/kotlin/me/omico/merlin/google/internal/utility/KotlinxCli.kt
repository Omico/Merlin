// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
package me.omico.merlin.google.internal.utility

import kotlinx.cli.ArgType
import java.nio.file.Path
import kotlin.io.path.Path

object PathType : ArgType<Path>(hasParameter = true) {
    override val description: kotlin.String = "{ Path }"
    override fun convert(value: kotlin.String, name: kotlin.String): Path = Path(value)
}
