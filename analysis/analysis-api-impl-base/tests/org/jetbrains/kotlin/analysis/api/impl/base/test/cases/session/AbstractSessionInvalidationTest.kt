/*
 * Copyright 2010-2025 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.impl.base.test.cases.session

import org.jetbrains.kotlin.analysis.api.platform.modification.KotlinModificationEventKind
import org.jetbrains.kotlin.analysis.api.projectStructure.KaModule
import org.jetbrains.kotlin.analysis.test.framework.base.AbstractAnalysisApiBasedTest
import org.jetbrains.kotlin.analysis.test.framework.directives.publishWildcardModificationEventsByDirective
import org.jetbrains.kotlin.analysis.test.framework.projectStructure.KtTestModule
import org.jetbrains.kotlin.analysis.test.framework.projectStructure.ktTestModuleStructure
import org.jetbrains.kotlin.test.services.TestServices
import org.jetbrains.kotlin.test.services.assertions

/**
 * Checks that sessions are invalidated after publishing modification events. The type of published modification event depends on the value
 * of [modificationEventKind]. This allows [AbstractSessionInvalidationTest] to check all modification event kinds with the same original
 * test data.
 *
 * [AbstractSessionInvalidationTest] is a base class for invalidation tests of `KaSession` and `LLFirSession`, which share the test
 * data but not necessarily the result data (see also [resultFileSuffix]).
 *
 * @param S The type of the session, either an LL FIR or an analysis session.
 */
abstract class AbstractSessionInvalidationTest<S> : AbstractAnalysisApiBasedTest() {
    /**
     * The kind of modification event to be published for the invalidation. Each modification event is tested separately and has its own
     * associated result file.
     */
    protected abstract val modificationEventKind: KotlinModificationEventKind

    /**
     * A suffix for the result file to distinguish it from the results of other session invalidation tests if the results are different.
     */
    protected abstract val resultFileSuffix: String?

    protected abstract fun getSessions(ktTestModule: KtTestModule): List<TestSession<S>>

    /**
     * In some cases, it might be legal for a session cache to evict sessions which are still valid. Such sessions would fail the validity
     * check (see [checkSessionsMarkedInvalid]) and should be skipped.
     */
    protected open fun shouldSkipValidityCheck(session: TestSession<S>): Boolean = false

    override fun doTest(testServices: TestServices) {
        val ktTestModules = testServices.ktTestModuleStructure.mainModules

        val sessionsBeforeModification = getAllSessions(ktTestModules)
        checkSessionValidityBeforeModification(sessionsBeforeModification, testServices)

        testServices.ktTestModuleStructure.publishWildcardModificationEventsByDirective(modificationEventKind)
        val sessionsAfterModification = getAllSessions(ktTestModules)

        val invalidatedSessions = buildSet {
            addAll(sessionsBeforeModification)
            removeAll(sessionsAfterModification)
        }

        checkInvalidatedModules(invalidatedSessions, testServices)
        checkSessionsMarkedInvalid(invalidatedSessions, testServices)

        val untouchedSessions = sessionsBeforeModification.intersect(sessionsAfterModification)
        checkUntouchedSessionValidity(untouchedSessions, testServices)
    }

    private fun getAllSessions(modules: List<KtTestModule>): List<TestSession<S>> = modules.flatMap(::getSessions)

    private fun checkInvalidatedModules(
        invalidatedSessions: Set<TestSession<S>>,
        testServices: TestServices,
    ) {
        val invalidatedSessionDescriptions = invalidatedSessions
            .map { it.description }
            .distinct()
            .sorted()

        val actualText = buildString {
            appendLine("Invalidated sessions:")
            invalidatedSessionDescriptions.forEach { appendLine(it) }
        }

        testServices.assertions.assertEqualsToTestDataFileSibling(
            actualText,
            extension = ".${modificationEventKind.name.lowercase()}.txt",

            // Support differing result data. Using `testPrefix` takes away the ability for different kinds of tests (such as IDE vs.
            // Standalone modes) to have different test results (since `testPrefix` normally supports this functionality), but (1) we are
            // currently only testing the IDE mode and (2) the test results between different modes should not differ for session
            // invalidation in the first place.
            testPrefixes = listOfNotNull(resultFileSuffix),
        )
    }

    private fun checkSessionValidityBeforeModification(
        sessions: List<TestSession<S>>,
        testServices: TestServices,
    ) {
        sessions.forEach { session ->
            testServices.assertions.assertTrue(session.isValid) {
                "The session `$session` should be valid before invalidation is triggered."
            }
        }
    }

    private fun checkSessionsMarkedInvalid(
        invalidatedSessions: Set<TestSession<S>>,
        testServices: TestServices,
    ) {
        invalidatedSessions.forEach { session ->
            if (shouldSkipValidityCheck(session)) return@forEach

            testServices.assertions.assertFalse(session.isValid) {
                "The invalidated session `${session}` should have been marked invalid."
            }
        }
    }

    private fun checkUntouchedSessionValidity(
        sessions: Set<TestSession<S>>,
        testServices: TestServices,
    ) {
        sessions.forEach { session ->
            testServices.assertions.assertTrue(session.isValid) {
                "The session `$session` has not been evicted from the session cache and should still be valid."
            }
        }
    }
}
