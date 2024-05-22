/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.rhizomedb

import org.jetbrains.kotlin.generators.generateTestGroupSuiteWithJUnit5
import org.jetbrains.rhizomedb.runners.AbstractRhizomedbFirPsiDiagnosticTest

fun main(args: Array<String>) {
    System.setProperty("java.awt.headless", "true")

    generateTestGroupSuiteWithJUnit5(args) {
        testGroup(
            "plugins/rhizomedb/tests-gen",
            "plugins/rhizomedb/testData"
        ) {
            // ------------------------------- diagnostics -------------------------------
            testClass<AbstractRhizomedbFirPsiDiagnosticTest> {
                model("firMembers")
            }
        }
    }
}

private const val OPTIONAL_COMPANION = "// OPTIONAL_COMPANION"
private const val comment = "// File generated by `org.jetbrains.rhizomedb.TestGeneratorKt`. DO NOT MODIFY MANUALLY\n"