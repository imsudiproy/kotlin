/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.targets.js.npm.resolver

import org.gradle.api.artifacts.ResolvedArtifact
import org.gradle.api.artifacts.ResolvedDependency
import org.gradle.api.initialization.IncludedBuild
import java.io.File
import java.io.Serializable

data class ExternalGradleDependency(
    val dependency: ResolvedDependency,
    val artifact: ResolvedArtifact,
) : Serializable

data class FileCollectionExternalGradleDependency(
    val files: Collection<File>,
    val dependencyVersion: String?,
) : Serializable

data class FileExternalGradleDependency(
    val dependencyName: String,
    val dependencyVersion: String,
    val file: File,
) : Serializable

/**
 * Used to manually declare task dependencies for
 * [org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinPackageJsonTask].
 *
 * Deprecated because it is impractical to determine whether a dependency is on a composite build (see KT-74735).
 * Dependencies on composite builds will be treated as [ExternalGradleDependency].
 */
@Deprecated("No longer used. This is an internal KGP utility that should not be used in user projects. Scheduled for removal in Kotlin 2.4.")
data class CompositeDependency(
    val dependencyName: String,
    val dependencyVersion: String,
    val includedBuildDir: File,
    @Transient
    val includedBuild: IncludedBuild?,
) : Serializable

data class InternalDependency(
    val projectPath: String,
    val compilationName: String,
    val projectName: String,
) : Serializable
