/*
 * Copyright 2010-2025 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.platform.projectStructure

import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.analysis.api.platform.KotlinPlatformComponent
import kotlin.reflect.KClass


/**
 * [KotlinGlobalSearchScopeMergeStrategy] is used to declare a strategy for
 * merging a list of [GlobalSearchScope]s into a single [GlobalSearchScope].
 * Is used by implementations of [KotlinGlobalSearchScopeMerger] to optimize the merging process and flatten the resulting scope.
 *
 * Note that [KotlinGlobalSearchScopeMergeStrategy] are applied in the order they are registered.
 */
public interface KotlinGlobalSearchScopeMergeStrategy<T : GlobalSearchScope> : KotlinPlatformComponent {
    /**
     * Defines the target type [KotlinGlobalSearchScopeMergeStrategy] works with.
     * All scopes passed to [mergeScopes] are guaranteed to be a subtype of [targetType].
     */
    public val targetType: KClass<T>

    /**
     * Merges a list of [GlobalSearchScope]s into an optimized set of [GlobalSearchScope]s.
     * - If [scopes] cannot be optimized, [mergeScopes] should return the same [scopes] list.
     * - If [scopes] can be merged into one [GlobalSearchScope.EMPTY_SCOPE], then [mergeScopes] should return an empty list.
     */
    public fun mergeScopes(scopes: List<T>): List<GlobalSearchScope>

    public companion object {
        public val EP_NAME: ExtensionPointName<KotlinGlobalSearchScopeMergeStrategy<*>> =
            ExtensionPointName<KotlinGlobalSearchScopeMergeStrategy<*>>(
                "org.jetbrains.kotlin.kotlinGlobalSearchScopeMergeStrategy"
            )

        public fun getMergeStrategies(
            project: Project,
        ): List<KotlinGlobalSearchScopeMergeStrategy<*>> = EP_NAME.getExtensionList(project)
    }
}

