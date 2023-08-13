// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
package me.omico.merlin.google.internal.utility

import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createDirectories
import kotlin.io.path.deleteRecursively
import kotlin.io.path.exists

@OptIn(ExperimentalPathApi::class)
internal fun Path.clearDirectory() {
    if (exists()) deleteRecursively()
    createDirectories()
}
