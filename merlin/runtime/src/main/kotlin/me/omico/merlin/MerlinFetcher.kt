// Copyright 2023 Omico
// SPDX-License-Identifier: Apache-2.0
package me.omico.merlin

import java.net.URL
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeBytes
import kotlin.io.path.writeText

object MerlinFetcher {
    private const val BASE_URL = "https://merlin.omico.me"

    fun refresh(localSaveDirectory: Path, indexFile: IndexFile): Status {
        val localHashFile = localSaveDirectory.resolve(indexFile.hash)
        val localIndexFile = localSaveDirectory.resolve(indexFile.name)
        val result = runCatching {
            val remoteHash = URL("$BASE_URL/${indexFile.hash}").readText()
            if (localHashFile.exists() && remoteHash == localHashFile.readText()) return Status.UpToDate(remoteHash)
            URL("$BASE_URL/${indexFile.name}").readBytes().let(localIndexFile::writeBytes)
            remoteHash
        }
        val remoteHash = result.getOrElse { exception -> return Status.Failure(exception = exception) }
        localHashFile.writeText(remoteHash)
        return Status.Success(remoteHash)
    }

    sealed interface Status {
        data class Success(
            val hash: String,
        ) : Status

        data class Failure(
            val exception: Throwable,
        ) : Status

        data class UpToDate(
            val hash: String,
        ) : Status
    }
}
