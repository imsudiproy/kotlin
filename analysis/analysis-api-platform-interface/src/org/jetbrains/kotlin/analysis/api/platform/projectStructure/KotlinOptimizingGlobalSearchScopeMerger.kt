/*
 * Copyright 2010-2025 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.platform.projectStructure

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope

public class KotlinOptimizingGlobalSearchScopeMerger(private val project: Project) : KotlinGlobalSearchScopeMerger {
    private fun <T : GlobalSearchScope> Collection<GlobalSearchScope>.applyStrategy(strategy: KotlinGlobalSearchScopeMergeStrategy<T>): List<GlobalSearchScope> {
        val applicableScopes = this.filterIsInstance(strategy.targetType.java)
        val restScopes = this - applicableScopes
        return strategy.mergeScopes(applicableScopes) + restScopes
    }

    override fun union(scopes: Collection<GlobalSearchScope>): GlobalSearchScope {
        when {
            scopes.isEmpty() -> return GlobalSearchScope.EMPTY_SCOPE
            scopes.size == 1 -> return scopes.first()
        }

        val providedStrategies =
            KotlinGlobalSearchScopeMergeStrategy.getMergeStrategies(project)

        val resultingScopes = providedStrategies.fold(scopes) { scopes, strategy ->
            scopes.applyStrategy(strategy)
        }

        return GlobalSearchScope.union(resultingScopes)
    }
}