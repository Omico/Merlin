// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
package me.omico.merlin.cli.internal

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import java.nio.file.Path
import kotlin.io.path.writeBytes

@OptIn(ExperimentalSerializationApi::class)
internal inline fun <reified T : Any> Path.saveAsProtoBuf(value: T): Unit =
    writeBytes(ProtoBuf.encodeToByteArray(value))
