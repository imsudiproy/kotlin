/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.targets.js.testing

import org.gradle.api.file.Directory
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.process.ProcessForkOptions
import org.jetbrains.kotlin.gradle.internal.testing.TCServiceMessagesClientSettings
import org.jetbrains.kotlin.gradle.internal.testing.TCServiceMessagesTestExecutionSpec
import org.jetbrains.kotlin.gradle.targets.js.KotlinWasmTargetType
import org.jetbrains.kotlin.gradle.targets.js.RequiredKotlinJsDependency
import org.jetbrains.kotlin.gradle.targets.js.internal.parseNodeJsStackTraceAsJvm
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsPlugin.Companion.kotlinNodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTestFramework.Companion.createTestExecutionSpecDeprecated
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTestFramework.Companion.CREATE_TEST_EXEC_SPEC_DEPRECATION_MSG
import org.jetbrains.kotlin.gradle.targets.js.webTargetVariant
import org.jetbrains.kotlin.gradle.targets.js.writeWasmUnitTestRunner
import org.jetbrains.kotlin.gradle.utils.getFile
import org.jetbrains.kotlin.gradle.utils.processes.ProcessLaunchOptions
import org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsPlugin.Companion.kotlinNodeJsEnvSpec as wasmKotlinNodeJsEnvSpec

internal class KotlinWasmNode(
    kotlinJsTest: KotlinJsTest,
    private val objects: ObjectFactory,
    private val providers: ProviderFactory,
) : KotlinJsTestFramework {
    override val settingsState: String = "KotlinWasmNode"

    private val testPath = kotlinJsTest.path

    @Transient
    override val compilation: KotlinJsIrCompilation = kotlinJsTest.compilation

    @Transient
    private val nodeJsEnvSpec = compilation.webTargetVariant(
        { compilation.project.kotlinNodeJsEnvSpec },
        { compilation.project.wasmKotlinNodeJsEnvSpec },
    )

    private val projectLayout = kotlinJsTest.project.layout

    override val workingDir: Provider<Directory> =
        if (compilation.target.wasmTargetType != KotlinWasmTargetType.WASI) {
            compilation.npmProject.dir
        } else {
            projectLayout.dir(kotlinJsTest.inputFileProperty.asFile.map { it.parentFile })
        }

    override val executable: Provider<String> = nodeJsEnvSpec.executable

    override fun createTestExecutionSpec(
        task: KotlinJsTest,
        launchOpts: ProcessLaunchOptions,
        nodeJsArgs: MutableList<String>,
        debug: Boolean,
    ): TCServiceMessagesTestExecutionSpec {
        val testRunnerFile = writeWasmUnitTestRunner(
            workingDir = launchOpts.workingDir.getFile(),
            compiledFile = task.inputFileProperty.get().asFile,
        )

        val clientSettings = TCServiceMessagesClientSettings(
            task.name,
            testNameSuffix = task.targetName,
            prependSuiteName = true,
            stackTraceParser = ::parseNodeJsStackTraceAsJvm,
            ignoreOutOfRootNodes = true,
        )

        val cliArgs = KotlinTestRunnerCliArgs(
            include = task.includePatterns,
            exclude = task.excludePatterns
        )

        val args = mutableListOf<String>()
        with(args) {
            addAll(nodeJsArgs)
            add(testRunnerFile.absolutePath)
            addAll(cliArgs.toList())
        }

        return TCServiceMessagesTestExecutionSpec(
            processLaunchOptions = launchOpts,
            processArgs = args,
            checkExitCode = false,
            clientSettings = clientSettings,
            dryRunArgs = args + "--dryRun",
        )
    }

    override val requiredNpmDependencies: Set<RequiredKotlinJsDependency> = emptySet()

    override fun getPath(): String = "$testPath:kotlinTestFrameworkStub"

    @Deprecated(message = CREATE_TEST_EXEC_SPEC_DEPRECATION_MSG)
    override fun createTestExecutionSpec(
        task: KotlinJsTest,
        forkOptions: ProcessForkOptions,
        nodeJsArgs: MutableList<String>,
        debug: Boolean,
    ): TCServiceMessagesTestExecutionSpec =
        createTestExecutionSpecDeprecated(
            task = task,
            forkOptions = forkOptions,
            nodeJsArgs = nodeJsArgs,
            debug = debug,
            objects = objects,
            providers = providers,
        )
}
