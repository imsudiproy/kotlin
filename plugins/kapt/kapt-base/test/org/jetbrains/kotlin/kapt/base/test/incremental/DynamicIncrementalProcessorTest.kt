/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kapt.base.test.incremental

import org.jetbrains.kotlin.kapt.base.incremental.RuntimeProcType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class DynamicIncrementalProcessorTest : AbstractTestWithGeneratedSourcesDir() {
    @Test
    fun testIfIsolating() {
        val srcFiles = listOf("User.java", "Address.java", "Observable.java").map { File(TEST_DATA_DIR, it) }
        val dynamic = DynamicProcessor(kind = RuntimeProcType.ISOLATING).toDynamic()
        runAnnotationProcessing(srcFiles, listOf(dynamic), generatedSources)

        assertEquals(RuntimeProcType.ISOLATING, dynamic.getRuntimeType())

        assertEquals(
            mapOf(
                generatedSources.resolve("test/UserGenerated.java") to "test.User",
                generatedSources.resolve("test/AddressGenerated.java") to "test.Address"
            ),
            dynamic.getGeneratedToSources()
        )
    }

    @Test
    fun testIfAggregating() {
        val srcFiles = listOf("User.java", "Address.java", "Observable.java").map { File(TEST_DATA_DIR, it) }
        val dynamic = DynamicProcessor(kind = RuntimeProcType.AGGREGATING).toDynamic()
        runAnnotationProcessing(srcFiles, listOf(dynamic), generatedSources)

        assertEquals(RuntimeProcType.AGGREGATING, dynamic.getRuntimeType())

        assertEquals(
            mapOf(
                generatedSources.resolve("test/UserGenerated.java") to null,
                generatedSources.resolve("test/AddressGenerated.java") to null
            ),
            dynamic.getGeneratedToSources()
        )
    }

    @Test
    fun testIfNonIncremental() {
        val srcFiles = listOf("User.java", "Address.java", "Observable.java").map { File(TEST_DATA_DIR, it) }
        val dynamic = DynamicProcessor(kind = RuntimeProcType.NON_INCREMENTAL).toDynamic()
        runAnnotationProcessing(srcFiles, listOf(dynamic), generatedSources)

        assertEquals(RuntimeProcType.NON_INCREMENTAL, dynamic.getRuntimeType())
        assertEquals(
            emptyMap<File, String?>(),
            dynamic.getGeneratedToSources()
        )
    }
}
