// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
package me.omico.merlin

@JvmInline
value class IndexFile(val id: String) {
    val name: String
        get() = "$id-index"

    val hash: String
        get() = "$name.hash"

    val json: String
        get() = "$name.json"
}
