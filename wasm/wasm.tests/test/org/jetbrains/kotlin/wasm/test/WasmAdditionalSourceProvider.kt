/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.wasm.test

import org.jetbrains.kotlin.config.LanguageFeature
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.test.directives.JsEnvironmentConfigurationDirectives
import org.jetbrains.kotlin.test.directives.WasmEnvironmentConfigurationDirectives
import org.jetbrains.kotlin.test.directives.model.RegisteredDirectives
import org.jetbrains.kotlin.test.model.TestFile
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.*
import java.io.File
import java.io.FileFilter
import java.nio.file.Paths

class WasmWasiBoxTestHelperSourceProvider(testServices: TestServices) : AdditionalSourceProvider(testServices) {
    override fun produceAdditionalFiles(globalDirectives: RegisteredDirectives, module: TestModule): List<TestFile> {
        val boxTestRunFile = File("wasm/wasm.tests/wasiBoxTestRun.kt")
        val p = /*org.jetbrains.kotlin.wasm.test.converters.*/extractTestPackage(module, testServices)
        if (p == null) 
            return emptyList()

        if (p == "") 
            return listOf(boxTestRunFile.toTestFile())


        return listOf(TestFile(
            boxTestRunFile.name,
            boxTestRunFile.useLines { it.joinToString("\n") }.replace("box()", "$p.box()"),
            originalFile = boxTestRunFile,
            startLineNumberInOriginalFile = 0,
            isAdditional = true,
            directives = RegisteredDirectives.Empty
        ))
    }
}

fun extractTestPackage(module: TestModule, testServices: TestServices): String? {
    val ktFiles = module.let { module ->
        module.files
            .filter { it.isKtFile }
            .map {
                val project = testServices.compilerConfigurationProvider.getProject(module)
                testServices.sourceFileProvider.getKtFileForSourceFile(it, project)
            }
    }

    val fileWithBoxFunction = ktFiles.find { file ->
        file.declarations.find { it is KtNamedFunction && it.name == "box" } != null
    } ?: return null

    return fileWithBoxFunction.packageFqName.asString()
}


class WasmAdditionalSourceProvider(testServices: TestServices) : AdditionalSourceProvider(testServices) {
    override fun produceAdditionalFiles(globalDirectives: RegisteredDirectives, module: TestModule): List<TestFile> {
        if (WasmEnvironmentConfigurationDirectives.NO_COMMON_FILES in module.directives) return emptyList()
        // For multiplatform projects, add the files only to common modules with no dependencies.
        if (module.languageVersionSettings.supportsFeature(LanguageFeature.MultiPlatformProjects) &&
            module.allDependencies.isNotEmpty()) {
            return emptyList()
        }
        return getAdditionalKotlinFiles(module.files.first().originalFile.parent).map { it.toTestFile() }
    }

    companion object {
        private const val COMMON_FILES_NAME = "_common"
        private const val COMMON_FILES_DIR = "_commonFiles/"
        private const val COMMON_FILES_DIR_PATH = "wasm/wasm.tests/$COMMON_FILES_DIR"

        private fun getFilesInDirectoryByExtension(directory: String, extension: String): List<String> {
            val dir = File(directory)
            if (!dir.isDirectory) return emptyList()

            return dir.listFiles(FileFilter { it.extension == extension })?.map { it.absolutePath } ?: emptyList()
        }

        private fun getAdditionalFiles(directory: String, extension: String): List<File> {
            val globalCommonFiles = getFilesInDirectoryByExtension(COMMON_FILES_DIR_PATH, extension).map { File(it) }
            val localCommonFilePath = "$directory/$COMMON_FILES_NAME.$extension"
            val localCommonFile = File(localCommonFilePath).takeIf { it.exists() } ?: return globalCommonFiles
            return globalCommonFiles + localCommonFile
        }

        fun getAdditionalKotlinFiles(directory: String): List<File> {
            return getAdditionalFiles(directory, KotlinFileType.EXTENSION)
        }
    }
}