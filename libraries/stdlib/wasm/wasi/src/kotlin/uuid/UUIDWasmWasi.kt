/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package kotlin.uuid

import kotlin.random.wasiRawRandomGet
import kotlin.wasm.WasiError
import kotlin.wasm.WasiErrorCode
import kotlin.wasm.unsafe.withScopedMemoryAllocator

internal actual fun secureRandomUUID(): UUID {
    withScopedMemoryAllocator { allocator ->
        var memory = allocator.allocate(UUID.SIZE_BYTES)
        val ret = wasiRawRandomGet(memory.address.toInt(), UUID.SIZE_BYTES)
        return if (ret == 0) {
            val randomBytes = ByteArray(UUID.SIZE_BYTES) {
                memory.loadByte().also { memory += 1 }
            }
            uuidFromRandomBytes(randomBytes)
        } else {
            throw WasiError(WasiErrorCode.entries[ret])
        }
    }
}
