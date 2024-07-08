/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.pipeline

import org.jetbrains.kotlin.diagnostics.KtDiagnostic
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.collectors.DiagnosticCollectorComponents
import org.jetbrains.kotlin.fir.analysis.collectors.SimpleDiagnosticsCollector
import org.jetbrains.kotlin.fir.analysis.collectors.components.DiagnosticComponentsFactory
import org.jetbrains.kotlin.fir.analysis.collectors.components.DiagnosticComponentsFactory.CompilationMode.*
import org.jetbrains.kotlin.fir.analysis.collectors.components.LossDiagnosticCollectorComponent
import org.jetbrains.kotlin.fir.analysis.collectors.components.ReportCommitterDiagnosticComponent
import org.jetbrains.kotlin.fir.declarations.FirFile
import org.jetbrains.kotlin.fir.resolve.ScopeSession
import org.jetbrains.kotlin.fir.resolve.SessionHolder
import org.jetbrains.kotlin.fir.resolve.SessionHolderImpl
import org.jetbrains.kotlin.fir.resolve.transformers.FirTotalResolveProcessor
import org.jetbrains.kotlin.fir.withFileAnalysisExceptionWrapping

fun FirSession.runResolution(firFiles: List<FirFile>): Pair<ScopeSession, List<FirFile>> {
    val resolveProcessor = FirTotalResolveProcessor(this)
    resolveProcessor.process(firFiles)
    return resolveProcessor.scopeSession to firFiles
}

fun FirSession.runCheckers(
    scopeSession: ScopeSession,
    firFiles: Collection<FirFile>,
    reporter: BaseDiagnosticsCollector,
) = firFiles.groupBy { it.moduleData.session }.forEach { (declarationSiteSession, files) ->
    runCheckers(scopeSession, files, reporter, mode = Platform(SessionHolderImpl.createWithEmptyScopeSession(declarationSiteSession)))
}

fun FirSession.runCheckers(
    scopeSession: ScopeSession,
    firFiles: Collection<FirFile>,
    reporter: BaseDiagnosticsCollector,
    mode: DiagnosticComponentsFactory.CompilationMode,
): Map<FirFile, List<KtDiagnostic>> {
    val collector = DiagnosticComponentsFactory.create(this, scopeSession, mode)
    collector.collectDiagnosticsInSettings(reporter)
    for (file in firFiles) {
        withFileAnalysisExceptionWrapping(file) {
            collector.collectDiagnostics(file, reporter)
        }
    }
    return firFiles.associateWith {
        val path = it.sourceFile?.path ?: return@associateWith emptyList()
        reporter.diagnosticsByFilePath[path] ?: emptyList()
    }
}

fun FirSession.collectLostDiagnosticsOnFile(
    scopeSession: ScopeSession,
    file: FirFile,
    reporter: BaseDiagnosticsCollector,
    declarationSiteSessionHolder: SessionHolder,
): List<KtDiagnostic> {
    val collector = SimpleDiagnosticsCollector(this, scopeSession) { reporter ->
        DiagnosticCollectorComponents(
            commonComponents = emptyList(),
            expectComponents = emptyList(),
            listOf(LossDiagnosticCollectorComponent(this, reporter)),
            ReportCommitterDiagnosticComponent(this, reporter),
            declarationSiteSessionHolder,
        )
    }
    withFileAnalysisExceptionWrapping(file) {
        collector.collectDiagnostics(file, reporter)
    }
    val path = file.sourceFile?.path ?: return emptyList()
    return reporter.diagnosticsByFilePath[path] ?: emptyList()
}
