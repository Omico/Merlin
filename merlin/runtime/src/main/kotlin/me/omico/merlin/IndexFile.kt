// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
package me.omico.merlin

@JvmInline
value class IndexFile(val name: String) {
    val hash: String
        get() = "$name.hash"
}
