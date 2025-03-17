/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.test.services.sourceProviders

import org.jetbrains.kotlin.test.directives.AdditionalFilesDirectives
import org.jetbrains.kotlin.test.directives.model.DirectivesContainer
import org.jetbrains.kotlin.test.directives.model.RegisteredDirectives
import org.jetbrains.kotlin.test.model.TestFile
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.AdditionalSourceProvider
import org.jetbrains.kotlin.test.services.TestModuleStructure
import org.jetbrains.kotlin.test.services.TestServices
import org.jetbrains.kotlin.utils.addToStdlib.runIf
import java.io.File
import java.net.URI
import java.nio.file.FileSystems

class IrInterpreterHelpersSourceFilesProvider(testServices: TestServices) : AdditionalSourceProvider(testServices) {
    companion object {
        private val HELPERS_PATH = "ir/interpreter/helpers"
        private val STDLIB_PATH = (System.getProperty("stdlib.path") ?: "./libraries/stdlib")
        private val UNSIGNED_PATH = arrayOf(
            "$STDLIB_PATH/unsigned/src/kotlin",
            "$STDLIB_PATH/jvm/src/kotlin/util/UnsignedJVM.kt"
        )
        private val RUNTIME_PATHS = arrayOf(
            "$STDLIB_PATH/src/kotlin/ranges/Progressions.kt",
            "$STDLIB_PATH/src/kotlin/ranges/ProgressionIterators.kt",
            "$STDLIB_PATH/src/kotlin/internal/progressionUtil.kt",
            "$STDLIB_PATH/jvm/runtime/kotlin/TypeAliases.kt",
            "$STDLIB_PATH/jvm/runtime/kotlin/text/TypeAliases.kt",
            "$STDLIB_PATH/jvm/src/kotlin/collections/TypeAliases.kt",
            "$STDLIB_PATH/src/kotlin/text/regex/MatchResult.kt",
            "$STDLIB_PATH/src/kotlin/collections/Sequence.kt",
        )
        private val ANNOTATIONS_PATHS = arrayOf(
            "$STDLIB_PATH/src/kotlin/annotations/WasExperimental.kt",
            "$STDLIB_PATH/src/kotlin/annotations/ExperimentalStdlibApi.kt",
            "$STDLIB_PATH/src/kotlin/annotations/OptIn.kt",
            "$STDLIB_PATH/src/kotlin/internal/Annotations.kt",
            "$STDLIB_PATH/src/kotlin/experimental/inferenceMarker.kt",
            "$STDLIB_PATH/jvm/runtime/kotlin/jvm/annotations/JvmPlatformAnnotations.kt",
        )
        private val REFLECT_PATH = "$STDLIB_PATH/jvm/src/kotlin/reflect"
        private val EXCLUDES = listOf(
            "src/kotlin/UStrings.kt", "src/kotlin/UMath.kt", "src/kotlin/UNumbers.kt", "src/kotlin/reflect/TypesJVM.kt",
            "libraries/stdlib/unsigned/src/kotlin/UnsignedCommon.kt",
        ).map(::File)
    }

    override val directiveContainers: List<DirectivesContainer> =
        listOf(AdditionalFilesDirectives)

    private fun getTestFilesForEachDirectory(vararg directories: String): List<TestFile> {
        val stdlibPath = File(STDLIB_PATH).canonicalPath
        return directories.flatMap { directory ->
            File(directory)
                .also { check(it.exists()) { "$it path is not found" } }
                .walkTopDown().mapNotNull { file ->
                    val parentPath = file.parentFile.canonicalPath
                    val relativePath = runIf(parentPath.startsWith(stdlibPath)) { parentPath.removePrefix("$stdlibPath/") }
                    file.takeUnless { it.isDirectory }
                        ?.takeUnless { EXCLUDES.any { file.endsWith(it) } }
                        ?.toTestFile(relativePath = relativePath)
                }.toList()
        }
    }

    override fun produceAdditionalFiles(
        globalDirectives: RegisteredDirectives,
        module: TestModule,
        testModuleStructure: TestModuleStructure
    ): List<TestFile> {
        return getTestFilesForEachDirectory(
            *UNSIGNED_PATH,
            *RUNTIME_PATHS,
            *ANNOTATIONS_PATHS,
            REFLECT_PATH
        ) + this::class.java.classLoader.getResource(HELPERS_PATH)!!.let {
            val resourceUri = it.toURI()
            when (resourceUri.scheme) {
                "jar" -> handleJarFileSystem(resourceUri)
                "file" -> handleFileSystem(resourceUri)
                else -> throw UnsupportedOperationException("Unsupported URI scheme: ${resourceUri.scheme}")
            }

        }
    }

    private fun handleFileSystem(resourceUri: URI): List<TestFile> {
        return File(resourceUri).walkTopDown()
            .mapNotNull { file -> file.takeIf { it.isFile }?.toTestFile() }
            .toList()
    }

    private fun handleJarFileSystem(resourceUri: URI): List<TestFile> {
        val array = resourceUri.toString().split("!")
        val fs = FileSystems.newFileSystem(URI.create(array[0]), emptyMap<String, String>())
        return fs.getPath(array[1]).toFile().walkTopDown()
            .mapNotNull { file -> file.toTestFile() }
            .toList()
    }
}
