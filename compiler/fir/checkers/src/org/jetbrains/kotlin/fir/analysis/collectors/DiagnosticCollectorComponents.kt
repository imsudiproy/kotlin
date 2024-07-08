/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.analysis.collectors

import org.jetbrains.kotlin.fir.analysis.collectors.components.AbstractDiagnosticCollectorComponent
import org.jetbrains.kotlin.fir.analysis.collectors.components.ReportCommitterDiagnosticComponent
import org.jetbrains.kotlin.fir.resolve.SessionHolder

class DiagnosticCollectorComponents(
    val commonComponents: List<AbstractDiagnosticCollectorComponent>,
    val expectComponents: List<AbstractDiagnosticCollectorComponent>,
    val regularComponents: List<AbstractDiagnosticCollectorComponent>,
    val reportCommitter: ReportCommitterDiagnosticComponent,
    val declarationSiteSessionHolder: SessionHolder?,
)