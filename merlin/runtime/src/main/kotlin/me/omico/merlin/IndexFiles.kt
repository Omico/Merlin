// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
package me.omico.merlin

object IndexFiles {
    val Google: IndexFile = IndexFile("google")
    val Omico: IndexFile = IndexFile("omico")
    val All: Set<IndexFile> = setOf(
        Google,
        Omico,
    )
}
